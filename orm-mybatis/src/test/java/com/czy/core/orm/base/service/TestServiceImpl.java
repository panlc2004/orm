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
public class TestServiceImpl extends BaseServiceImpl<TestEntity> implements TestService{
    @Transactional
    public void insertList2(List<TestEntity> recordList) {
        int limit = 8000;
        int per = recordList.size() / limit;
        int i = 0;
        int res = 0;
        for (; i < per; i++) {
            res += getMapper().insertList(recordList.subList(i * limit, (i + 1) * limit));
        }
        res += getMapper().insertList(recordList.subList(per * limit, recordList.size()));
        System.out.println(res);

    }

    @Transactional
    public void insertList3(List<TestEntity> recordList) {
        for (TestEntity t : recordList) {
            getMapper().insert(t);
        }
    }
}
