package org.example.cloud.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.entities.PayVO;
import org.example.cloud.resp.ResultData;
import org.example.cloud.service.PayService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Tag(name = "PayController", description = "PayController")
@RefreshScope
public class PayCircuitController {
    @Resource
    private PayService payService;

    @GetMapping(value = "/pay/circuit/{id}")

    public ResultData<PayVO> getById(@PathVariable("id") Integer id) {
        log.info("request in {}", DateUtil.now());
        if (id < 0) {
            throw new RuntimeException("id should >= 0");
        }
        log.info("request out {}", DateUtil.now());
        return ResultData.success(payService.getById(id));
    }

    private volatile int tag = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @GetMapping(value = "/pay/bulkhead/{id}")
    public ResultData<PayVO> bulkhead(@PathVariable("id") Integer id) {
        log.info("request in {}", DateUtil.now());

        if (id < 0) {
            throw new IllegalArgumentException("id should be >= 0");
        }

        // 使用定时器代替新线程中的睡眠
        scheduler.schedule(() -> {
            while (tag == 0) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 保持中断状态
                    throw new RuntimeException(e);
                }
                log.info("Waiting..." + DateUtil.now()); // 记录等待信息
            }
        }, 0, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(5);
            tag = 1;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 保持中断状态
            throw new RuntimeException(e);
        }

        log.info("request out {}", DateUtil.now());
        return ResultData.success(payService.getById(id));
    }


}
