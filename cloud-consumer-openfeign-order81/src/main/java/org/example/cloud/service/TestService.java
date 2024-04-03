package org.example.cloud.service;

import org.example.cloud.resp.ResultData;

public interface TestService {
    ResultData testBulkheadS(Integer id) ;
}
