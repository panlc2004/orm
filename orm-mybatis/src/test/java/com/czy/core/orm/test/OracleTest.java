package com.czy.core.orm.test;

import com.czy.core.orm.base.QueryParams;
import com.czy.core.orm.entity.TestEntity;
import com.czy.core.orm.mapper.OracleMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by panlc on 2017-03-24.
 */
public class OracleTest {

    static ClassPathXmlApplicationContext ctx;
    static OracleMapper oracleMapper;

    @BeforeClass
    public static void beforeClass() {
        ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"});
        ctx.start();
        oracleMapper = ctx.getBean(OracleMapper.class);
    }

    @Test
    public void testInsertAndDeleteByPrimaryKeyAndUpdate() {
        TestEntity testEntity = new TestEntity();
//        testEntity.setName("tsetest");
        long insert = oracleMapper.insert(testEntity);
        Assert.assertTrue(testEntity.getId() != null);
        int i = oracleMapper.updateByPrimaryKey(testEntity);
        Assert.assertEquals(1, i);
        int delete = oracleMapper.deleteByPrimaryKey(testEntity.getId());
        Assert.assertEquals(1, delete);
    }

    @Test
    public void testSelectByPrimaryKey() {
        TestEntity testEntity = new TestEntity();
        testEntity.setName("tsetest");
        long insert = oracleMapper.insert(testEntity);
        Long id = testEntity.getId();
        TestEntity testEntity1 = oracleMapper.selectByPrimaryKey(id);
        Assert.assertTrue(id == testEntity1.getId());
    }

    @Test
    public void testSelectListByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        queryParams.orderBy("id").asc().orderBy("name").desc();
        queryParams.selectProperties("name");
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andEqualTo("name", "123");
        criteria.andLike("name", "%et%");
        criteria.andBetween("id", 10, 13);
//        criteria.andCondition("1=-1");
        List<TestEntity> testEntities = oracleMapper.selectListByParams(queryParams);
        System.out.println(testEntities);
    }

    @Test
    public void testSelectOneByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
//        queryParams.selectProperties("name");
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andEqualTo("id", 27);
        TestEntity testEntity = oracleMapper.selectOneByParams(queryParams);
        System.out.println(testEntity);
    }

    @Test
    public void testDeleteByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        QueryParams.Criteria criteria = queryParams.createCriteria();
//        criteria.andEqualTo("name", "et");
        int i = oracleMapper.deleteByParams(queryParams);
        Assert.assertTrue(i == 0);
    }

    @Test
    public void testUpdateSelectiveByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andEqualTo("name", "et");
        TestEntity testEntity = new TestEntity();
        testEntity.setName("tsetest");
        int i = oracleMapper.updateSelectiveByParams(testEntity, null);
    }

    @Test
    public void testUpdateByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andEqualTo("name", "et");
        TestEntity testEntity = new TestEntity();
        int i = oracleMapper.updateByParams(testEntity, queryParams);
    }

    @Test
    public void testSelectCountByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andEqualTo("name", "tsetest");
        int i = oracleMapper.selectCountByParams(queryParams);
        System.out.println(i);
    }



    @Test
    public void testSelectRelativeByPrimaryKey() {
        TestEntity testEntity = oracleMapper.selectRelativeByPrimaryKey(2);
        System.out.println(testEntity);
    }

    @Test
    public void testSelectListRelativeByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        queryParams.orderBy("id").asc().orderBy("name").desc();
        QueryParams.Criteria or = queryParams.or();
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andLike("name", "%et%").andCondition("ONE_one.id = 1");
        or.andEqualTo("id", 12);
        List<TestEntity> testEntities = oracleMapper.selectListRelativeByParams(queryParams);
        System.out.println(testEntities);
    }

    @Test
    public void testSelectOneRelativeByParams() {
        QueryParams queryParams = new QueryParams(TestEntity.class);
        QueryParams.Criteria criteria = queryParams.createCriteria();
        criteria.andLike("name", "%et%").andCondition("ONE_oneList.id = 3");
//        criteria.andEqualTo("id", 20);
        TestEntity testEntity = oracleMapper.selectOneRelativeByParams(queryParams);
        System.out.println(testEntity);
    }


}
