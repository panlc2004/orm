package com.czy.core.orm.base.service;

import java.util.List;

/**
 * Created by PLC on 2017/4/30.
 */
public interface BaseService<T> {
    void insertList(List<T> recordList);
}
