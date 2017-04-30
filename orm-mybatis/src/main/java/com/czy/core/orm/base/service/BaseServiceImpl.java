package com.czy.core.orm.base.service;

import com.czy.core.orm.base.mapper.BaseMapper;
import com.czy.core.orm.tool.NullUtil;
import com.czy.core.orm.tool.SpringContextHelper;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import javax.annotation.PostConstruct;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by PLC on 2017/4/30.
 */
@EnableTransactionManagement
public class BaseServiceImpl<T> implements BaseService<T> {

    private BaseMapper<T> mapper;

    public BaseMapper<T> getMapper() {
        return mapper;
    }

    @PostConstruct
    public void init() {
        Class<?> entityClass = getSuperClassGenricType(this.getClass(), 0);
        String mapperName = getMapperName();
        if (NullUtil.isEmpty(mapperName)) {
            mapperName = entityClass.getSimpleName() + "Mapper";
        }
        mapper = SpringContextHelper.getBeanById(mapperName.substring(0,1).toLowerCase() + mapperName.substring(1,mapperName.length()));
    }

    /**
     * 指定对应的mapper名称
     * 不重写时，本service默认从Spring容器中查找id为：泛型实体类名 + Mapper 的mapper实例
     *
     * @return
     */
    String getMapperName() {
        return null;
    }

    private Class<Object> getSuperClassGenricType(final Class clazz, final int index) {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (params[index] instanceof TypeVariableImpl) {
            GenericDeclaration genericDeclaration = ((TypeVariableImpl) params[index]).getGenericDeclaration();
            genericDeclaration.getTypeParameters()[index].getName();
            return null;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    @Transactional(value = "tm-default", rollbackFor = Exception.class)
    public void insertList(List<T> recordList) {
        int limit = 8000;
        int per = recordList.size() / limit;
        int i = 0;
        for (; i < per; i++) {
            getMapper().insertList(recordList.subList(i * limit, (i + 1) * limit));
        }
        getMapper().insertList(recordList.subList(per * limit, recordList.size()));
    }

}
