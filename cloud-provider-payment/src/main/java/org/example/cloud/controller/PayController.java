package org.example.cloud.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.entities.PayDO;
import org.example.cloud.entities.PayDTO;
import org.example.cloud.entities.PayVO;
import org.example.cloud.resp.ResultData;
import org.example.cloud.service.PayService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "PayController", description = "PayController")
@RefreshScope
public class PayController {
    @Resource
    private PayService payService;

    @PostMapping(value = "/pay/add")
    @Operation(summary = "addPay", description = "addPay")
    public ResultData<Integer> addPay(@RequestBody PayDO payDO) {
        return ResultData.success(payService.add(payDO));
    }

    @DeleteMapping(value = "/pay/del/{id}")
    public ResultData<Integer> delPay(@PathVariable("id") Integer id) {
        return ResultData.success(payService.delete(id));
    }

    @DeleteMapping(value = "/pay/update")
    public ResultData<Integer> delPay(@RequestBody PayDTO payDTO) {
        PayDO payDO = new PayDO();
        BeanUtils.copyProperties(payDTO, payDO);
        return ResultData.success(payService.update(payDO));
    }

    @GetMapping(value = "/pay/get/{id}")
    public ResultData<PayVO> getById(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("id should >= 0");
        }
        return ResultData.success(payService.getById(id));
    }

    @Value("${test}")
    private String test;

    @GetMapping(value = "/pay/get/consul")
    public ResultData<String> getFromConsul() {
        return ResultData.success(test);
    }

    @GetMapping(value = "/pay/getAll")
    public ResultData<List<PayVO>> getAll() {
        return ResultData.success(payService.getAll());
    }
}
