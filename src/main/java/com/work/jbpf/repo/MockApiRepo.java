/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.work.jbpf.repo;

import com.work.jbpf.entity.MockApi;
import com.work.jbpf.entity.MockApiId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author NMSLAP415
 */
@Repository
public interface MockApiRepo extends JpaRepository<MockApi, MockApiId>{
    public List<MockApi> findByApiCollection(String apiCollection);
    public List<MockApi> findByApiCollectionAndRequestURL(String apiCollection, String requestURL);
}
