package com.monalisa.domain.admin.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminApi {

    @PostMapping("/test")
    public String test() {
        System.out.println("Admin Test");
        return "test";
    }
}
