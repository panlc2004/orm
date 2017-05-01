package com.czy.core.orm.config.mybatis;

import com.czy.core.orm.config.datasource.DataSourceBuilder;
import com.czy.core.orm.tool.SpringContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PLC on 2017/5/1.
 */
public class MybatisAtomikosConfig extends MybatisConfig {

    @Autowired
    public MybatisAtomikosConfig(SpringContextHelper springContextHelper, DataSourceBuilder dataSourceBuilder) {
        super(springContextHelper, dataSourceBuilder);
    }

    @Override
    public void registerDynamicTransactionManager() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("transactionTimeout", 300);
        springContextHelper.addBean(com.atomikos.icatch.jta.UserTransactionImp.class, "atomikosUserTransaction", map, null, null);

        map.clear();
        map.put("userTransaction", SpringContextHelper.getBeanById("atomikosUserTransaction"));
        springContextHelper.addBean(org.springframework.transaction.jta.JtaTransactionManager.class, "transactionManager", map, null, null);
    }
}
