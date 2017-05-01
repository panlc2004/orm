package com.czy.core.orm.ato;

import com.czy.core.orm.base.service.BaseService;
import com.czy.core.orm.entity.TestEntity;

import java.util.List;

/**
 * Created by PLC on 2017/5/1.
 */
public interface AtoTestService extends BaseService<TestEntity> {
    int insertBetweenDiffDb(List<TestEntity> recordList);
}
