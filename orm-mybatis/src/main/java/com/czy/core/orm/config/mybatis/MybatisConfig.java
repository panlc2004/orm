package com.czy.core.orm.config.mybatis;

import com.czy.core.orm.config.datasource.DataSourceBuilder;
import com.czy.core.orm.tool.NullUtil;
import com.czy.core.orm.tool.SpringContextHelper;
import com.czy.core.orm.tool.SpringPropertiesUtil;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

/**
 * Created by panlc on 2017-02-16.
 */
public class MybatisConfig {

    private static Map<String, String> sqlSessionDialect = new HashMap<String, String>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpringContextHelper springContextHelper;

    @Autowired
    private DataSourceBuilder dataSourceBuilder;

    public String getSqlSessionDialect(String sqlSessionName) {
        return sqlSessionDialect.get(sqlSessionName);
    }

    @PostConstruct
    public void registerTransactionManager() {
        registerDynamicTransactionManager();
        registerDynamicSqlSessionFactory();
    }

    /**
     * 动态注册各数据源Transactionmanager
     */
    private void registerDynamicTransactionManager() {
        Map<String, String> customDataSources = dataSourceBuilder.getDatasourceDialect();
        for (String key : customDataSources.keySet()) {
            registerTransactionmanager(key);
        }
    }

    /**
     * 为数据源创建Transactionmanager
     *
     * @param dataSourceName
     */
    private void registerTransactionmanager(String dataSourceName) {
        String tranBeanName = "tm-" + getDatasourceName(dataSourceName);
        try {
            DataSource dataSource = springContextHelper.getBeanById(dataSourceName);
//            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dataSource", dataSource);
            springContextHelper.addBean(DataSourceTransactionManager.class, tranBeanName, map, null, null);
        } catch (Exception e) {
            logger.warn("error occurred while register {} to spring", tranBeanName, e);
        }
    }

    /**
     * 动态注册数据源Transactionmanager
     */
    private void registerDynamicSqlSessionFactory() {
        Map<String, String> datasourceDialect = dataSourceBuilder.getDatasourceDialect();
        for (String key : datasourceDialect.keySet()) {
            registerSqlSessionFactory(key);
        }
    }

    /**
     * 为不同数据源创建SqlSessionFactory
     *
     * @param dataSourceName
     */
    private void registerSqlSessionFactory(String dataSourceName) {
        String sqlSessionFactoryName = "sqlSessionFactory-" + getDatasourceName(dataSourceName);
        try {
            DataSource dataSource = springContextHelper.getBeanById(dataSourceName);
            Map<String, Object> resource = new HashMap<String, Object>();
            resource.put("dataSource", dataSource);
//            resource.put("plugins", new ProcedureSqlInt());       //TODO 添加插件
            //加载mybatis config文件
            String configLocation = SpringPropertiesUtil.getStringProperty("mybatis.configurationLocations");
            if (NullUtil.isEmpty(configLocation)) {
                configLocation = "classpath:core/mybatis-config.xml";
            }
            resource.put("configLocation", configLocation);

            //加载mapper文件
            String mapperLocations = SpringPropertiesUtil.getStringProperty("mybatis.mapperLocations");
            if (NullUtil.isNotEmpty(mapperLocations)) {
                String[] mapperLocationsConfig = mapperLocations.split(",");
                resource.put("mapperLocations", Arrays.asList(mapperLocationsConfig));
            }

            //TODO 增加插件

            springContextHelper.addBean(SqlSessionFactoryBean.class, sqlSessionFactoryName, resource, null, null);
            sqlSessionDialect.put(sqlSessionFactoryName, dataSourceBuilder.getDialect(dataSourceName)); //TODO注册sqlSessionFactory类型
        } catch (Exception e) {
            logger.error("error occurred while register {} to spring", sqlSessionFactoryName, e);
            throw new RuntimeException("error occurred while register " + sqlSessionFactoryName + " to spring", e);
        }
    }


    public Resource[] resolveMapperLocations(String... mapperLocationsConfig) {
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocationsConfig != null) {
            for (String mapperLocation : mapperLocationsConfig) {
                Resource[] mappers;
                try {
                    mappers = new PathMatchingResourcePatternResolver().getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {

                }
            }
        }

        Resource[] mapperLocations = new Resource[resources.size()];
        mapperLocations = resources.toArray(mapperLocations);
        return mapperLocations;
    }

    /**
     * 取数据源名称
     *
     * @param beanName
     * @return
     */
    private String getDatasourceName(String beanName) {
        if (beanName == null || "".equals(beanName)) {
            throw new RuntimeException("beanName can't be null or empty");
        }
        int i = beanName.indexOf("-");
        return beanName.substring(i + 1, beanName.length());
    }

}
