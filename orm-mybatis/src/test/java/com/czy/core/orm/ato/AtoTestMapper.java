package com.czy.core.orm.ato;

import com.czy.core.orm.base.mapper.BaseMapper;
import com.czy.core.orm.config.mybatis.annotations.AutoMapper;
import com.czy.core.orm.entity.TestEntity;

/**
 * Created by PLC on 2017/5/1.
 */
@AutoMapper("ds1")
public interface AtoTestMapper extends BaseMapper<TestEntity> {

}
