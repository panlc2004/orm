package com.czy.core.orm.ato;

import com.czy.core.orm.base.service.BaseServiceImpl;
import com.czy.core.orm.base.service.TestService;
import com.czy.core.orm.entity.TestEntity;
import com.czy.core.orm.mapper.MySqlMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by PLC on 2017/5/1.
 */
@Service()
public class AtoTestServiceImpl extends BaseServiceImpl<TestEntity> implements AtoTestService {

    @Resource
    private TestService testService;

    @Transactional
    public int insertBetweenDiffDb(List<TestEntity> recordList) {
        int i = recordList.size() / 2;
        super.insertList(recordList);
        testService.insertList(recordList);
        return 12;
    }

    @Override
    public String getMapperName() {
        return "AtoTestMapper";
    }
}
