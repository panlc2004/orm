package com.czy.core.orm.sql.template;

import com.czy.core.orm.config.datasource.IdentityDialect;
import com.czy.core.orm.config.mybatis.MybatisConfig;
import com.czy.core.orm.tool.SpringContextHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * SqlTemplate创建工场
 * Created by panlc on 2017-03-20.
 */
public class SqlTemplateFactory {

    private SqlTemplateFactory() {

    }

    private static Map<String, AbstractSqlTemplate> templateCache = new HashMap<String, AbstractSqlTemplate>();

    /**
     * 获取sqlTemplate
     *
     * @param sqlSessionFactoryBeanName sqlSessionFactory名称
     * @return
     */
    public static AbstractSqlTemplate getSqlTemplate(String sqlSessionFactoryBeanName) {
        if (!templateCache.containsKey(sqlSessionFactoryBeanName)) {
            templateCache.put(sqlSessionFactoryBeanName, createSqlTemplate(sqlSessionFactoryBeanName));
        }
        return templateCache.get(sqlSessionFactoryBeanName);
    }

    /**
     * 创建sqlTemplate
     *
     * @param sqlSessionFactoryBeanName
     * @return
     */
    private static AbstractSqlTemplate createSqlTemplate(String sqlSessionFactoryBeanName) {
        String dialect = getDialect(sqlSessionFactoryBeanName);
        IdentityDialect identityDialect;
        try {
            identityDialect = IdentityDialect.valueOf(dialect);
        } catch (Exception e) {
            throw new IllegalArgumentException("not supported dialect : " + dialect);
        }
        return identityDialect.getSqlTemplate();
    }

    /**
     * 根据sqlSessionFactory名称获取对应数据库类型
     * @param sqlSessionFactoryBeanName
     * @return
     */
    private static String getDialect(String sqlSessionFactoryBeanName) {
        MybatisConfig mybatisConfig = SpringContextHelper.getBeanById("mybatisConfig");
        String sqlSessionDialect = mybatisConfig.getSqlSessionDialect(sqlSessionFactoryBeanName);
        return sqlSessionDialect;
    }
}
