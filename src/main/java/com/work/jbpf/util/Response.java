/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.work.jbpf.util;

import java.util.Date;

/**
 *
 * @author NMSLAP415
 */
@lombok.Data
public class Response {
    private Object data;
    private Integer statusCode;
    private String message;
    private Date timestamp;
    private String path;
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private final Response response;

        public Builder() {
            response = new Response();
            response.setTimestamp(new Date());
        }
        
        public Builder statusCode(Integer statusCode) {
            response.setStatusCode(statusCode);
            return Builder.this;
        }
        public Builder data(Object data) {
            response.setData(data);
            return Builder.this;
        }
        public Builder message(String message) {
            response.setMessage(message);
            return Builder.this;
        }
        public Builder path(String path) {
            response.setPath(path);
            return Builder.this;
        }
        
        public Response build() {
            if (response.getStatusCode() == null) {
                throw new RuntimeException("StatusCode can not be null");
            }
            return this.response;
        }
    }
}
