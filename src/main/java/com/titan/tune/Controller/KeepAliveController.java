package com.titan.tune.Controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/server")
public class KeepAliveController {
    @GetMapping(path = "/ping")
    public ResponseEntity<?>responseServer(){
        return new ResponseEntity<>("...Server running....." , HttpStatusCode.valueOf(200)) ;
    }
}
