package com.example.cloud.service.impl;

import com.example.cloud.mapper.AccountMapper;
import com.example.cloud.service.AccountService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @SneakyThrows
    @Override
    public void decrease(Long userId, Long money) {
        log.info("======>AccountService start");

        accountMapper.decrease(userId, money);
        log.info("======>AccountService end");


        //TODO make a error
        //TimeUnit.SECONDS.sleep(20);//sleep to see this tx in seata portal
        //int a = 1 / 0;

    }

    private void timeout() {
        try {
            TimeUnit.SECONDS.sleep(65);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
