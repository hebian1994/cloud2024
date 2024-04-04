package org.example.cloud.service.impl;

import org.example.cloud.mapper.StorageMapper;
import org.example.cloud.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {
    @Resource
    private StorageMapper storageMapper;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("======>StorageService start");
        storageMapper.decrease(productId, count);
        log.info("======>StorageService end");
    }
}
