package com.czy.core.orm.mapper;

import com.czy.core.orm.base.mapper.BaseMapper;
import com.czy.core.orm.config.mybatis.annotations.AutoMapper;
import com.czy.core.orm.entity.TestEntity;

/**
 * Created by panlc on 2017-03-17.
 */
@AutoMapper("ds2")
public interface OracleMapper extends BaseMapper<TestEntity> {

}
