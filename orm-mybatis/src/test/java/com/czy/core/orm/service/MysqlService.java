package com.czy.core.orm.service;

import com.czy.core.orm.base.Procedure;
import com.czy.core.orm.base.ProcedureExecutor;
import com.czy.core.orm.entity.TestEntity;
import com.czy.core.orm.mapper.MySqlMapper;
import com.czy.core.orm.tool.SpringContextHelper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by panlc on 2017-03-31.
 */
@Service
@EnableTransactionManagement
public class MysqlService {

    @Resource
    private MySqlMapper mySqlMapper;

    @Resource
    private ProcedureExecutor procedureExecutor;

    @Transactional(value = "tm-default", rollbackFor = Exception.class)
    public void testRollback() throws Exception {
        TestEntity testEntity = new TestEntity();
        testEntity.setName("Transactional Test");
        TestEntity testEntity1 = new TestEntity();
        testEntity1.setName("Transactional Test2");
        mySqlMapper.insert(testEntity);
        mySqlMapper.insert(testEntity1);

        Procedure procedure = new Procedure(MySqlMapper.class);
        procedure.addInParams("a", 1);
        procedure.setProcedureName("pro3");

        procedureExecutor.execute(procedure);
        procedureExecutor.execute(procedure);

        Object result = procedure.getResult();
        System.out.println(result);

        String sqlSessionFactoryName = procedureExecutor.getSqlSessionFactoryName(MySqlMapper.class);
        SqlSessionFactory sqlSessionFactoryBean = SpringContextHelper.getBeanById(sqlSessionFactoryName);
        SqlSession sqlSession = sqlSessionFactoryBean.openSession();
    }

    @Transactional(value = "tm-default", rollbackFor = Exception.class)
    public void insertList(List<TestEntity> recordList) {
        mySqlMapper.insertList(recordList);
    }

    @Transactional(value = "tm-default", rollbackFor = Exception.class)
    public void insertList2(List<TestEntity> recordList) {
        for (TestEntity t : recordList) {
            mySqlMapper.insert(t);
        }
    }

}
