package com.czy.core.orm.mapper;

import com.czy.core.orm.base.Procedure;
import com.czy.core.orm.base.mapper.BaseMapper;
import com.czy.core.orm.config.mybatis.annotations.AutoMapper;
import com.czy.core.orm.entity.TestEntity;
import org.apache.ibatis.annotations.Select;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by panlc on 2017-03-17.
 */
@AutoMapper()
public interface MySqlMapper extends BaseMapper<TestEntity> {

    int insert2(TestEntity recore);

    TestEntity selectByPrimaryKey1(long key);

    TestEntity selectRelativeByPrimaryKey2(long key);

    Object callPro(Map<String, Object> params);
    Object callPro3(Map<String, Object> params);

    int insertList1(List<TestEntity> testEntities);

}
