/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.work.jbpf.rest;

import com.work.jbpf.entity.MockApi;
import com.work.jbpf.service.MockApiService;
import com.work.jbpf.util.Response;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author NMSLAP415
 */
@RestController
@RequestMapping(value = "/mockapi", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MockApiRest extends AbstractRest{
    
    @Autowired
    private MockApiService mockApiService;
    
    @GetMapping("/{apiCollection}")
    public Response getExistingEndPoints(@PathVariable("apiCollection") String apiCollection) {
        List<MockApi> mockApis = mockApiService.findAllEndPointByApiCollection(apiCollection);
        Response.Builder response = successResponseBuilder().data(mockApis);
        return response.build();
    }
    
    @PostMapping
    public Response create(@RequestBody MockApi mockApi) {
        mockApi = mockApiService.createMockApi(mockApi);
        Response.Builder response = successResponseBuilder().data(mockApi);
        return response.build();
    }
    
    @PutMapping
    public Response update(@RequestBody MockApi mockApi) {
        mockApi = mockApiService.updateMockApi(mockApi);
        Response.Builder response = successResponseBuilder().data(mockApi);
        return response.build();
    }
    @DeleteMapping
    public Response delete(@RequestBody MockApi mockApi) {
        mockApiService.delete(mockApi);
        Response.Builder response = successResponseBuilder().data(mockApi);
        return response.build();
    }
}
