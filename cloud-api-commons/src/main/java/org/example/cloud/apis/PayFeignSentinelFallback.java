package org.example.cloud.apis;

import org.example.cloud.entities.PayVO;
import org.example.cloud.resp.ResultData;
import org.example.cloud.resp.ReturnCodeEnum;
import org.springframework.stereotype.Component;

@Component
public class PayFeignSentinelFallback implements PayFeignSentinelApi {
    @Override
    public ResultData getPayByOrderNo(Integer orderNo) {
        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), "fallback");
    }
}
