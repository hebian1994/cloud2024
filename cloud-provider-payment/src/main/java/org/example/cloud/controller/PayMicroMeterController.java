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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Tag(name = "PayMicroMeterController", description = "PayMicroMeterController")
@RefreshScope
public class PayMicroMeterController {
    @Resource
    private PayService payService;

    @GetMapping(value = "/pay/micrometer/{id}")

    public ResultData<PayVO> getById(@PathVariable("id") Integer id) {
        log.info("request in {}", DateUtil.now());
        if (id < 0) {
            throw new RuntimeException("id should >= 0");
        }
        log.info("request out {}", DateUtil.now());
        return ResultData.success(payService.getById(id));
    }
}
