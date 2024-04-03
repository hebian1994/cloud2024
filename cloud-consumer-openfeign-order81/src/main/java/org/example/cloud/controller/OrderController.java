package org.example.cloud.controller;

import cn.hutool.core.date.DateUtil;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.example.cloud.apis.PayFeignApi;
import org.example.cloud.entities.PayDTO;
import org.example.cloud.resp.ResultData;
import org.example.cloud.service.TestService;
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

    @Resource
    private TestService testService;


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

    @GetMapping("/consumer/pay/circuit/{id}")
    @CircuitBreaker(name = "cloud-payment-service", fallbackMethod = "myCircuitFallback")
    public ResultData testCircuit(@PathVariable("id") Integer id) {
        System.out.println(DateUtil.now());
        ResultData payById = payFeignApi.circuit(id);
        System.out.println(DateUtil.now());
        return payById;
    }

    @GetMapping("/consumer/pay/bulkhead/{id}")
    public ResultData testBulkhead(@PathVariable("id") Integer id) {
        return testService.testBulkheadS(id);
    }


    public ResultData myCircuitFallback(Integer id, Throwable throwable) {
        System.out.println(id);
        System.out.println("myCircuitFallback");
        System.out.println(throwable);
        return ResultData.success("myCircuitFallback");
    }
}
