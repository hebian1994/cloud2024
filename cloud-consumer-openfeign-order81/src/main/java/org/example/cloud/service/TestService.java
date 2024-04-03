package org.example.cloud.service;

import org.example.cloud.resp.ResultData;

import java.util.concurrent.CompletableFuture;

public interface TestService {
    ResultData testBulkheadS(Integer id) ;

    CompletableFuture<ResultData> testFixedThreadPoolBulkhead(Integer id);

    ResultData ratelimiter(Integer id) ;
}
