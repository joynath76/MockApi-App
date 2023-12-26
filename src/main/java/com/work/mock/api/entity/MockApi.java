/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.work.mock.api.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author NMSLAP415
 */
@Entity
@Table(name = "MOCKAPI")
@IdClass(MockApiId.class)
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class MockApi implements Serializable {
    @Id
    @Column(name = "API_COLLECTION")
    private String apiCollection;
    
    @Id
    @Column(name = "REQUEST_METHOD")
    private String requestMethod;
    
    @Id
    @Column(name = "REQUEST_URL")
    private String requestURL;
    
    @Column(name = "RESPONSE_CODE")
    private Integer responseCode;
    
    @Column(name = "CONTENT_TYPE")
    private String contentType;
    
    @Lob
    @Column(name = "RESPONSE_BODY")
    private String responseBody;
    
    @Column(name = "CREATED_ON")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdOn;
    
    @Column(name = "UPDATED_ON")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updatedOn;
    
    
    @PrePersist
    public void onPrePersist(){
        createdOn = new Date();
    }
    
    @PreUpdate
    public void onPreUpdate(){
        updatedOn = new Date();
    }
}
