package com.czy.core.orm.config.datasource;

/**
 * Created by panlc on 2017-03-14.
 */
public enum DataSourcePoolType {

    druid {
        public String getPoolClass() {
            return "com.alibaba.druid.pool.DruidDataSource";
        }

        public String getInitMethod() {
            return null;
        }

        public String getDestroyMethod() {
            return null;
        }
    },

    dbcp {
        public String getPoolClass() {
            return null;
        }

        public String getInitMethod() {
            return null;
        }

        public String getDestroyMethod() {
            return null;
        }
    }
    ;


    public abstract String getPoolClass();

    public abstract String getInitMethod();

    public abstract String getDestroyMethod();
}
