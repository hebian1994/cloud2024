package org.example.cloud.controller;

import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import org.example.cloud.apis.PayFeignApi;
import org.example.cloud.entities.PayDTO;
import org.example.cloud.resp.ResultData;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class OrderController {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private PayFeignApi payFeignApi;


    @PostMapping("/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO) {
        return payFeignApi.addPay(payDTO);
    }

    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id) {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            instances.forEach(a -> System.out.println(a.getServiceId() + "--" + a.getHost() + "--" + a.getPort()));
        }

        System.out.println(DateUtil.now());
        ResultData payById = payFeignApi.getPayById(id);
        System.out.println(System.currentTimeMillis());
        return payById;
    }
}
