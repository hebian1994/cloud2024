package org.example.cloud.service.impl;

import cn.hutool.core.date.DateUtil;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import jakarta.annotation.Resource;
import org.example.cloud.apis.PayFeignApi;
import org.example.cloud.resp.ResultData;
import org.example.cloud.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Resource
    private PayFeignApi payFeignApi;

    @Override
    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "myBulkheadFallback", type = Bulkhead.Type.SEMAPHORE)
    public ResultData testBulkheadS(Integer id) {
        System.out.println(DateUtil.now());
        ResultData payById = payFeignApi.bulkhead(id);
        System.out.println(DateUtil.now());
        return payById;
    }


    public ResultData myBulkheadFallback(Integer id, Throwable throwable) {
        System.out.println(id);
        System.out.println("myBulkheadFallback");
        System.out.println(throwable);
        return ResultData.success("myBulkheadFallback");
    }
}
