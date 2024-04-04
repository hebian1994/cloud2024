package org.example.cloud.service.impl;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.apis.AccountFeignApi;
import org.example.cloud.apis.StoreFeignApi;
import org.example.cloud.entities.Order;
import org.example.cloud.mapper.OrderMapper;
import org.example.cloud.service.OrderService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StoreFeignApi storageFeignApi;
    @Resource
    private AccountFeignApi accountFeignApi;

    @Override
    @GlobalTransactional(name = "create-order-transaction", rollbackFor = Exception.class)
    public void create(Order order) {
        // get xid
        String xid = RootContext.getXID();

        // 1. create order
        log.info("======> 开始新建订单, XID: {}", xid);
        order.setStatus(0);
        int result = orderMapper.insertSelective(order);

        if (result > 0) {
            log.info("======> 新建订单成功, OrderInfo: {}", order);

            // 2. decrease store
            log.info("======> 开始扣减库存");
            storageFeignApi.decrease(order.getProductId(), order.getCount());
            log.info("======> 扣减库存成功");

            // 3. decrease account money
            log.info("======> 开始扣减余额");
            accountFeignApi.decrease(order.getUserId(), order.getMoney());
            log.info("======> 扣减余额成功");

            // 4. update order status
            log.info("======> 开始修改订单状态");
            Example whereCondition = new Example(Order.class);
            Example.Criteria criteria = whereCondition.createCriteria();
            criteria.andEqualTo("id", order.getId());
            criteria.andEqualTo("status", 0);
            order.setStatus(1);
            int updateResult = orderMapper.updateByExampleSelective(order, whereCondition);
            log.info("======> 修改订单状态成功 {}", updateResult);


        }
        log.info("======> 结束新建订单, XID: {}", xid);
    }
}
