package org.example.cloud.service.impl;

import jakarta.annotation.Resource;
import org.example.cloud.controller.PayController;
import org.example.cloud.convertor.PayConvertor;
import org.example.cloud.entities.PayDO;
import org.example.cloud.entities.PayVO;
import org.example.cloud.mapper.PayMapper;
import org.example.cloud.service.PayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl implements PayService {

    @Resource
    private PayMapper payMapper;


    @Override
    public int add(PayDO payDO) {
        return payMapper.insert(payDO);
    }

    @Override
    public int delete(Integer id) {
        return payMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(PayDO payDO) {
        return payMapper.save(payDO);
    }

    @Override
    public PayVO getById(Integer id) {
        return PayConvertor.toPayVO(payMapper.selectByPrimaryKey(id));
    }

    @Override
    public List<PayVO> getAll() {
        List<PayDO> payDOS = payMapper.selectAll();
        return payDOS.stream().map(PayConvertor::toPayVO).toList();
    }
}
