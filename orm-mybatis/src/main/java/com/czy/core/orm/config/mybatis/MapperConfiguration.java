package com.czy.core.orm.config.mybatis;

import com.czy.core.orm.config.DataBaseEnvInit;
import com.czy.core.orm.config.mybatis.annotations.AutoMapper;
import com.czy.core.orm.tool.NullUtil;
import com.czy.core.orm.tool.SpringPropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * Created by panlc on 2017-03-17.
 */
@Configuration
@Import({ AutoMapperScannerRegistrar.class })
public class MapperConfiguration implements BeanPostProcessor {

    private Log logger = LogFactory.getLog(MapperConfiguration.class);

    @Autowired
    private DataBaseEnvInit dataBaseEnvInit;    //让该类在DataBaseEnvInit初始化之后再进行初始化，低版本Spring中不可删除。无其他作用

    @Autowired
    private MybatisConfig mybatisConfig;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void scan() {
        logger.debug("Searching for mappers annotated with @AutoMapper'");
        DynamicDataSourceMapperScanner scanner = new DynamicDataSourceMapperScanner(AutoMapperScannerRegistrar.getRegistry());

        try {
            if (this.resourceLoader != null) {
                scanner.setResourceLoader(this.resourceLoader);
            }
            scanner.setAnnotationClass(AutoMapper.class);
            scanner.registerFilters();
//            scanner.doScan(StringUtils.toStringArray(pkgs));
            String config = SpringPropertiesUtil.getProperty("automapper.locations");
            if (NullUtil.isNotEmpty(config)) {
                String[] split = config.split(",");
                scanner.doScan(split);

            }
        } catch (IllegalStateException ex) {
            logger.debug("Could not determine auto-configuration " + "package, automatic mapper scanning disabled.");
        }
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
