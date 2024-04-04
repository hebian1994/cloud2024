package org.example.cloud.apis;

import org.example.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-account-service")
public interface AccountFeignApi {

    /**
     * 扣减账户余额
     */
    @PostMapping(value = "/account/decrease")
    ResultData decrease(@RequestParam("userId") Long userId
            , @RequestParam("money") Long money);
}
