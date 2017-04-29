package com.czy.core.orm.test;

import com.czy.core.orm.base.Procedure;
import com.czy.core.orm.base.QueryParams;
import com.czy.core.orm.entity.TestEntity;
import com.czy.core.orm.mapper.MySqlMapper;
import com.czy.core.orm.service.MysqlService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panlc on 2017-03-24.
 */
public class MySqlTest {

    static ClassPathXmlApplicationContext ctx;
    static MySqlMapper mySqlMapper;

    @BeforeClass
    public static void beforeClass() {
        ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"});
        ctx.start();
        mySqlMapper = ctx.getBean(MySqlMapper.class);
    }

    @Test
    public void testInsertAndDeleteByPrimaryKeyAndUpdate() {
        TestEntity testEntity = new TestEntity();
//        testEntity.setName("tsetest");
        long insert = mySqlMapper.insert(testEntity);
        Assert.assertTrue(testEntity.getId() != null);
        testEntity.setName("q23");
        int i = mySqlMapper.updateByPrimaryKey(testEntity);
        int delete = mySqlMapper.deleteByPrimaryKey(testEntity.getId());
    }

    @Test
    public void testSelectByPrimaryKey() {
        TestEntity testEntity = new TestEntity();
        testEntity.setName("tsetest");
        long insert = mySqlMapper.insert(testEntity);
        Long id = testEntity.getId();
        TestEntity testEntity1 = mySqlMapper.selectByPrimaryKey(id);
        Assert.assertTrue(id == testEntity1.getId());
    }

    @Test
    public void testSelectListByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        queryParams.orderBy("id").asc().orderBy("name").desc();
        queryParams.selectProperties("name");
        QueryParams.Criteria criteria = queryParams.createCriteria();
        QueryParams.Criteria or = queryParams.or();
        criteria.andEqualTo("name", "123");
        criteria.andLike("name", "%et%");
        criteria.andBetween("id", 10, 13);
        criteria.andCondition("1=-1");
        List<TestEntity> testEntities = mySqlMapper.selectListByParams(queryParams);
        System.out.println(testEntities);
    }

    @Test
    public void testSelectOneByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
//        queryParams.selectProperties("name");
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andEqualTo("id", 27);
        TestEntity testEntity = mySqlMapper.selectOneByParams(queryParams);
        System.out.println(testEntity.getId());
    }

    @Test
    public void testDeleteByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        QueryParams.Criteria criteria = queryParams.createCriteria();
//        criteria.andEqualTo("name", "et");
        int i = mySqlMapper.deleteByParams(queryParams);
        Assert.assertTrue(i == 0);
    }

    @Test
    public void testUpdateSelectiveByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andEqualTo("name", "et");
        TestEntity testEntity = new TestEntity();
        testEntity.setName("123");
        int i = mySqlMapper.updateSelectiveByParams(testEntity, null);
    }

    @Test
    public void testUpdateByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andEqualTo("name", "et");
        TestEntity testEntity = new TestEntity();
        int i = mySqlMapper.updateByParams(testEntity, queryParams);
    }

    @Test
    public void testSelectCountByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andEqualTo("name", "tsetest");
        int i = mySqlMapper.selectCountByParams(queryParams);
        System.out.println(i);
    }

    @Test
    public void testSelect() {
        TestEntity testEntity = mySqlMapper.selectByPrimaryKey1(1);
        System.out.println(testEntity);
    }

    @Test
    public void testSelectRelativeByPrimaryKey() {
        TestEntity testEntity = mySqlMapper.selectRelativeByPrimaryKey(2);
        System.out.println(testEntity);
        TestEntity testEntity2 = mySqlMapper.selectRelativeByPrimaryKey(2);
        System.out.println(testEntity2);

    }

    @Test
    @Transactional()
    public void testSelectListRelativeByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        queryParams.orderBy("id").asc().orderBy("name").desc();
//        QueryParams.Criteria or = queryParams.or();
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andLike("name", "%et%").andCondition("ONE_one.id = 1");
//        or.andEqualTo("id", 12);
        List<TestEntity> testEntities = mySqlMapper.selectListRelativeByParams(queryParams);
        System.out.println(testEntities);
    }

    @Test
    public void testSelectOneRelativeByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andLike("name", "%et%").andCondition("ONE_oneList.id = 3");
        criteria.andEqualTo("id", 20);
        TestEntity testEntity = mySqlMapper.selectOneRelativeByParams(queryParams);
        System.out.println(testEntity);
    }

    @Test
    public void testSelectRelativeByPrimaryKey2() {
        TestEntity testEntity = mySqlMapper.selectRelativeByPrimaryKey2(2);
        System.out.println(testEntity);
    }

    @Test
    public void testCallPro() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("a", 1);
        Object i = mySqlMapper.callPro(map);
        System.out.println(i);
    }

    @Test
    public void testCallPro3() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("a", 1);
        Object i = mySqlMapper.callPro3(map);
        System.out.println(i);
    }

    @Test
    public void testRollback() throws Exception {
        MysqlService mysqlService = (MysqlService) ctx.getBean("mysqlService");
        mysqlService.testRollback();

    }

    @Test
    public void tset4() throws Exception {
        List<TestEntity> testEntities = mySqlMapper.selectListByParams(null);
        System.out.println(testEntities);
    }


}
