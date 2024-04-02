package org.example.cloud.apis;

import org.example.cloud.entities.PayDTO;
import org.example.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//@FeignClient(value = "cloud-payment-service")
@FeignClient(value = "cloud-payment-service", configuration = FeignConfig.class)
public interface PayFeignApi {

    @PostMapping(value = "/pay/add")
    public ResultData addPay(@RequestBody PayDTO payDTO);

    @GetMapping(value = "/pay/get/{id}")
    public ResultData getPayById(@PathVariable("id") Integer id);
}
