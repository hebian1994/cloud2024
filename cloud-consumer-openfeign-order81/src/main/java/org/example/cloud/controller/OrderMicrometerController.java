package org.example.cloud.controller;


import jakarta.annotation.Resource;
import org.example.cloud.resp.ResultData;
import org.example.cloud.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderMicrometerController {

    @Resource
    private TestService testService;

    @GetMapping("/consumer/order/micrometer/{id}")
    public ResultData ratelimiter(@PathVariable("id") Integer id) {
        return testService.micrometer(id);
    }

}
