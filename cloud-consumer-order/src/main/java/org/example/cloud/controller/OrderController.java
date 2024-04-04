package org.example.cloud.controller;

import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;
import org.example.cloud.entities.PayDTO;
import org.example.cloud.resp.ResultData;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class OrderController {
    public static final String Pay_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;


    @PostMapping("/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO) {
        return restTemplate.postForObject(Pay_URL + "/pay/add", payDTO, ResultData.class);
    }

    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id) {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            instances.forEach(a -> System.out.println(a.getServiceId() + "--" + a.getHost() + "--" + a.getPort()));
        }

        return restTemplate.getForObject(Pay_URL + "/pay/get/" + id, ResultData.class, id);
    }


}
