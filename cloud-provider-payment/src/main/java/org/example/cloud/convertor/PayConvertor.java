package org.example.cloud.convertor;

import org.example.cloud.entities.PayDO;
import org.example.cloud.entities.PayVO;

public class PayConvertor {
    public static PayVO toPayVO(PayDO payDO) {
        if (payDO == null) {
            return null;
        }
        PayVO payVO = new PayVO();
        payVO.setId(payDO.getId());
        payVO.setPayNo(payDO.getPayNo());
        payVO.setOrderNo(payDO.getOrderNo());
        payVO.setUserId(payDO.getUserId());
        payVO.setAmount(payDO.getAmount());

        // Not mapped PayDO fields:
        // deleted
        // createTime
        // updateTime
        return payVO;
    }
}
