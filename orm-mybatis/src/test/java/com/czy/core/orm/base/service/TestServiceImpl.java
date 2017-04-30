package com.czy.core.orm.base.service;

import com.czy.core.orm.entity.TestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PLC on 2017/4/30.
 */
@Service
@EnableTransactionManagement
public class TestServiceImpl extends BaseServiceImpl<TestEntity> implements TestService{
    @Transactional(value = "tm-default")
    public void insertList2(List<TestEntity> recordList) {
        int limit = 8000;
        int per = recordList.size() / limit;
        int i = 0;
        for (; i < per; i++) {
            getMapper().insertList(recordList.subList(i * limit, (i + 1) * limit));
        }
        getMapper().insertList(recordList.subList(per * limit, recordList.size()));
    }
}
