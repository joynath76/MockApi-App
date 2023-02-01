/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.work.jbpf.entity;

import java.io.Serializable;

/**
 *
 * @author NMSLAP415
 */
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class MockApiId implements Serializable{
    private String apiCollection;
    private String requestMethod;
    private String requestURL;
}
