package org.example.cloud.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.entities.PayVO;
import org.example.cloud.resp.ResultData;
import org.example.cloud.service.PayService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "PayGatewayController", description = "PayGatewayController")
@RefreshScope
public class PayGatewayController {
    @Resource
    private PayService payService;

    @GetMapping(value = "/pay/gateway/get/{id}")

    public ResultData<PayVO> getById(@PathVariable("id") Integer id) {
        log.info("request in {}", DateUtil.now());
        if (id < 0) {
            throw new RuntimeException("id should >= 0");
        }
        log.info("request out {}", DateUtil.now());
        return ResultData.success(payService.getById(id));
    }

    @GetMapping(value = "/pay/gateway/info/{id}")

    public ResultData<PayVO> info(@PathVariable("id") Integer id) {
        log.info("request in {}", DateUtil.now());
        if (id < 0) {
            throw new RuntimeException("id should >= 0");
        }
        log.info("request out {}", DateUtil.now());
        return ResultData.success(payService.getById(id));
    }
}
