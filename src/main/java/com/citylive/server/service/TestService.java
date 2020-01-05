package com.citylive.server.service;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/testservice")
public class TestService {

    @RequestMapping("/hi")
    public ResponseEntity hi(){
        return ResponseEntity.ok("Hi! Welcome To Citylive");
    }

}
