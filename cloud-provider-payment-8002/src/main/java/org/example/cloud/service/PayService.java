package org.example.cloud.service;

import org.example.cloud.entities.PayDO;
import org.example.cloud.entities.PayVO;

import java.util.List;

public interface PayService {
    public int add(PayDO payDO);

    public int delete(Integer id);

    public int update(PayDO payDO);

    public PayVO getById(Integer id);

    public List<PayVO> getAll();

}
