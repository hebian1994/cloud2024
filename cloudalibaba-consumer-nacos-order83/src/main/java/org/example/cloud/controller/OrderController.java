package org.example.cloud.controller;

import jakarta.annotation.Resource;
import org.example.cloud.apis.PayFeignSentinelApi;
import org.example.cloud.resp.ResultData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    @Resource
    private RestTemplate restTemplate;


    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @GetMapping("/order/pay/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(serverURL + "/pay/nacos/get" + id, String.class);
    }

    @Resource
    private PayFeignSentinelApi feignSentinelApi;

    @GetMapping("/order/pay/sentinel/{id}")
    public ResultData paySentinel(@PathVariable("id") Integer id) {
        return feignSentinelApi.getPayByOrderNo(id);
    }


}
