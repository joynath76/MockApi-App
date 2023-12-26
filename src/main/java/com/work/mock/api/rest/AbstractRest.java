/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.work.mock.api.rest;

import com.work.mock.api.util.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author NMSLAP415
 */
@Slf4j
public class AbstractRest {
    @Autowired
    HttpServletRequest request;
    
    public String getRequestUrlString() {
        String reqString = "";
        if (request != null) {
            String contextPath = request.getContextPath();
            String url = request.getServletPath();
            String query = request.getQueryString();
            query = query == null ? "" : "?" + query;
            reqString = contextPath + url + query;
        }
        return reqString;
    }
    
    public Response.Builder successResponseBuilder() {
        Response.Builder builder = Response.builder();
        builder.path(getRequestUrlString());
        builder.statusCode(HttpServletResponse.SC_OK);
        return builder;
    }
}
