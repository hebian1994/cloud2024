package org.example.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.example.cloud.entities.PayDTO;
import org.example.cloud.entities.PayVO;
import org.example.cloud.resp.ResultData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayAlibabaController {
    @Value("${server.port}")
    private String test;

    @GetMapping("/pay/nacos/get{id}")
    public String getNacos(@PathVariable("id") Integer id) {
        System.out.println(DateUtil.now() + "request in ");
        return id + "get from nacos" + test;
    }

    //openfeign + sentinel
    @GetMapping("/pay/order/get/{orderNo}")
    @SentinelResource(value = "getPayByOrderNo", blockHandler = "handlerBlockHandler")
    public ResultData getPayByOrderNo(@PathVariable("orderNo") Integer orderNo) {
        System.out.println(DateUtil.now() + "request in ");

        PayVO payVO = new PayVO();
        payVO.setPayNo(orderNo + "get from getPayByOrderNo" + test);

        return ResultData.success(payVO);
    }

    public ResultData handlerBlockHandler(@PathVariable("orderNo") Integer orderNo, BlockException e) {
        e.printStackTrace();
        PayVO payVO = new PayVO();
        payVO.setPayNo(orderNo + "get from getPayByOrderNo handlerBlockHandler" + test);

        return ResultData.success(payVO);
    }
}
