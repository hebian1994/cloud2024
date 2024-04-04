package org.example.cloud.controller;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayAlibabaController {
    @Value("${server.port}")
    private String test;

    @GetMapping("/pay/nacos/get{id}")
    public String getNacos(@PathVariable("id") Integer id) {
        System.out.println(DateUtil.now() + "request in ");
        return id + "get from nacos" + test;
    }
}
