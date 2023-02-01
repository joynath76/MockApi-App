/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.work.jbpf.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.work.jbpf.entity.MockApi;
import com.work.jbpf.service.MockApiService;
import com.work.jbpf.util.Response;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *
 * @author NMSLAP415
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{
    @Autowired
    private MockApiService mockApiService;
    @Autowired
    private ObjectMapper objectMapper;
    
    
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity methodNotSupportedException(HttpServletRequest request,HttpRequestMethodNotSupportedException exception){
        log.debug("Method not Supported Exception {}", exception);
        Response.Builder res = Response.builder().message(exception.getMessage()).statusCode(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        log.debug("Response {}", res);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).contentType(MediaType.APPLICATION_JSON).body(res.build());
    }
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity noHandlerException(HttpServletRequest request, HttpServletResponse response,Exception exception) throws HttpRequestMethodNotSupportedException{
        Response.Builder res = Response.builder().message(exception.getMessage()).statusCode(HttpServletResponse.SC_NOT_FOUND);
        ResponseEntity responseEntity =  ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(res.build());
        String requestUrl = request.getRequestURI();
        List<String> chunkOfEndPoint = Arrays.asList(requestUrl.split("/"));
        log.debug("Request url chunks {}", chunkOfEndPoint);
        if(!chunkOfEndPoint.isEmpty() && "mock".equals(chunkOfEndPoint.get(1))){
            if(chunkOfEndPoint.size() <= 2){
                return responseEntity;
            }
            String apiCollection = chunkOfEndPoint.get(2);
            StringBuilder sb = new StringBuilder();
            chunkOfEndPoint.subList(3, chunkOfEndPoint.size()).forEach(chunks -> {
                sb.append("/").append(chunks);
            });
            String requestURL = sb.toString();
            log.debug("Fetching MockApi Entity for apiCollection {}, and request endPoint {}", apiCollection, requestURL);
            List<MockApi> mockApis = mockApiService.findMockApisByApiCollectionAndRequestURL(apiCollection, requestURL);
            log.debug("Mock APIS present for collection {}, results {}", apiCollection, mockApis);
            if(mockApis.isEmpty()){
                return responseEntity;
            }
            MockApi mockApi = mockApis.stream().filter(mockapi -> request.getMethod().equals(mockapi.getRequestMethod())).findAny().orElse(null);
            if(mockApi != null){
                log.debug("Sending mock api result {}", mockApi.getResponseBody());
                try {
                    Object resBody = objectMapper.readValue(mockApi.getResponseBody(), Object.class);
                    String contentType = mockApi.getContentType();
                    List<String> contentTypeChunks = Arrays.asList(contentType.split("/"));
                    return ResponseEntity.status(mockApi.getResponseCode()).contentType(new MediaType(contentTypeChunks.get(0), contentTypeChunks.get(1))).body(resBody);
                } catch (JsonProcessingException ex) {
                    return ResponseEntity.status(mockApi.getResponseCode()).contentType(MediaType.APPLICATION_JSON).body(mockApi.getResponseBody());
                }
            }else{
                HttpRequestMethodNotSupportedException methodNotSupportedException = new HttpRequestMethodNotSupportedException(request.getMethod());
                return methodNotSupportedException(request, methodNotSupportedException);
            }
        }
        return responseEntity;
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleAllException(HttpServletRequest request,Exception exception){
        log.debug("All Exception {}", exception);
        Response.Builder res = Response.builder()
                .message(exception.getMessage())
                .statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(res.build());
    }
    
    
    
}
