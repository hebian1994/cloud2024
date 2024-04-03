package org.example.cloud.service.impl;

import cn.hutool.core.date.DateUtil;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.example.cloud.apis.PayFeignApi;
import org.example.cloud.resp.ResultData;
import org.example.cloud.service.TestService;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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

    @SneakyThrows
    @Override
    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "myPoolBulkheadFallback", type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<ResultData> testFixedThreadPoolBulkhead(Integer id) {
        System.out.println(DateUtil.now());
        System.out.println("thread :" + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(3);
        ResultData payById = payFeignApi.bulkhead(id);

        CompletableFuture<ResultData> future = CompletableFuture.supplyAsync(() -> payFeignApi.bulkhead(id));
        System.out.println(DateUtil.now());
        return future;
    }

    @Override
    @RateLimiter(name = "cloud-payment-service", fallbackMethod = "myRateLimiterFallback")
    public ResultData ratelimiter(Integer id) {
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

    public CompletableFuture<ResultData> myPoolBulkheadFallback(Integer id, Throwable throwable) {
        System.out.println(id);
        System.out.println("myPoolBulkheadFallback");
        System.out.println(throwable);
        return CompletableFuture.supplyAsync(() -> ResultData.success("myPoolBulkheadFallback"));
    }

    public ResultData myRateLimiterFallback(Integer id, Throwable throwable) {
        System.out.println(id);
        System.out.println("myBulkheadFallback");
        System.out.println(throwable);
        return ResultData.success("myBulkheadFallback");
    }
}
