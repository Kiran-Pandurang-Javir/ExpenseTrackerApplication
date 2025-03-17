package org.kp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/healthcheck")
    public String healthCheck(){
        return "HI";
    }
}
