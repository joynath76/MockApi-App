/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.work.jbpf.service;

import com.work.jbpf.entity.MockApi;
import com.work.jbpf.entity.MockApiId;
import com.work.jbpf.repo.MockApiRepo;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author NMSLAP415
 */
@Service
public class MockApiService {
    
    @Autowired
    private MockApiRepo mockApiRepo;
    
    public List<MockApi> findAllEndPointByApiCollection(String apiCollection){
        return mockApiRepo.findByApiCollection(apiCollection);
    }
    
    public List<MockApi> findMockApisByApiCollectionAndRequestURL(String apiCollection, String requestURL){
        return mockApiRepo.findByApiCollectionAndRequestURL(apiCollection, requestURL);
    }
    
    public MockApi createMockApi(MockApi mockApi){
        MockApi exmockApi = mockApiRepo.findById(new MockApiId(mockApi.getApiCollection(), mockApi.getRequestMethod(), mockApi.getRequestURL())).orElse(null);
        if(exmockApi == null){
            return mockApiRepo.save(mockApi);
        }
        return exmockApi;
    }
    
    public MockApi updateMockApi(MockApi mockApi){
        MockApi exmockApi = mockApiRepo.findById(new MockApiId(mockApi.getApiCollection(), mockApi.getRequestMethod(), mockApi.getRequestURL())).orElse(null);
        if(exmockApi == null){
           throw new RuntimeException("Mock Api Not found");
        }
        exmockApi.setApiCollection(mockApi.getApiCollection());
        exmockApi.setRequestMethod(mockApi.getRequestMethod());
        exmockApi.setRequestURL(mockApi.getRequestURL());
        exmockApi.setContentType(mockApi.getContentType());
        exmockApi.setResponseCode(mockApi.getResponseCode());
        exmockApi.setResponseBody(mockApi.getResponseBody());
        exmockApi.setUpdatedOn(new Date());
        return mockApiRepo.save(exmockApi);
    }
    
    public void delete(MockApi mockApi){
        mockApiRepo.delete(mockApi);
    }
}
