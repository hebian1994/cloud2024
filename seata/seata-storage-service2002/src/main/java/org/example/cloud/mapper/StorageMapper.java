package org.example.cloud.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.cloud.entities.Storage;
import tk.mybatis.mapper.common.Mapper;

public interface StorageMapper extends Mapper<Storage> {
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
