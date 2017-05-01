package com.czy.core.orm.base.service;

import com.czy.core.orm.entity.TestEntity;

import java.util.List;

/**
 * Created by PLC on 2017/4/30.
 */
public interface TestService extends BaseService<TestEntity> {
    void insertList2(List<TestEntity> recordList);
    void insertList3(List<TestEntity> recordList);
}
