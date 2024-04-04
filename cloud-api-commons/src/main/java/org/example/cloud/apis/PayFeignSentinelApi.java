package org.example.cloud.apis;

import org.example.cloud.entities.PayVO;
import org.example.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-pay-provider", fallback = PayFeignSentinelFallback.class)
public interface PayFeignSentinelApi {
    @GetMapping("/pay/order/get/{orderNo}")
    ResultData getPayByOrderNo(@PathVariable("orderNo") Integer orderNo);
}
