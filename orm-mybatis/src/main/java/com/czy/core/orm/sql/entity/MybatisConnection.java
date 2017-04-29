package com.czy.core.orm.sql.entity;

import com.czy.core.orm.config.mybatis.annotations.Join;

/**
 * Created by panlc on 2017-03-30.
 */
public class MybatisConnection extends MybatisAssociation {

    public MybatisConnection(String mainTableName, String property, Class<?> targetClass, String fields, String columns, Join join) {
        super(mainTableName, property, targetClass, fields, columns, join);
    }

}
