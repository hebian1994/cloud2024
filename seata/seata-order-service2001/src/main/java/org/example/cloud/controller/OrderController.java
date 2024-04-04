package org.example.cloud.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.entities.Order;
import org.example.cloud.resp.ResultData;
import org.example.cloud.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    public ResultData<Order> create() {
        //create a new order for test
        Order order1 = new Order();
        order1.setProductId(123L);//产品编号
        order1.setCount(2);//购买数量
        order1.setMoney(10L);//总计多少钱
        order1.setUserId(1L);//用户ID
        order1.setStatus(0);//创建中


        orderService.create(order1);
        return ResultData.success(order1);
    }

}
