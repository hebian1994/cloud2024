package org.example.cloud.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.entities.PayDO;
import org.example.cloud.entities.PayDTO;
import org.example.cloud.entities.PayVO;
import org.example.cloud.service.PayService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PayController {
    @Resource
    private PayService payService;

    @PostMapping(value = "/pay/add")
    public Integer addPay(@RequestBody PayDO payDO) {
        return payService.add(payDO);
    }

    @DeleteMapping(value = "/pay/del/{id}")
    public Integer delPay(@PathVariable("id") Integer id) {
        return payService.delete(id);
    }

    @DeleteMapping(value = "/pay/update")
    public Integer delPay(@RequestBody PayDTO payDTO) {
        PayDO payDO = new PayDO();
        BeanUtils.copyProperties(payDTO, payDO);
        return payService.update(payDO);
    }

    @GetMapping(value = "/pay/get/{id}")
    public PayVO getById(@PathVariable("id") Integer id) {
        return payService.getById(id);
    }

    @GetMapping(value = "/pay/getAll")
    public List<PayVO> getAll() {
        return payService.getAll();
    }
}
