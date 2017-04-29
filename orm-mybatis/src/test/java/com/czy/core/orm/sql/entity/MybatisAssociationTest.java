package com.czy.core.orm.sql.entity;

import com.czy.core.orm.config.mybatis.annotations.Join;
import com.czy.core.orm.entity.One;
import com.czy.core.orm.entity.TestEntity;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by panlc on 2017-03-29.
 */
public class MybatisAssociationTest {

    @Test
    public void testNewInstance() {
        MybatisAssociation one = new MybatisAssociation("testRollback", "one",
                One.class, "name, id", "id=test_id,name=test_name", Join.LEFT);
        Assert.assertEquals("ONE", one.getTargetTableName());
        String joinCondition = one.getJoinCondition();
        Assert.assertEquals("left join testRollback.id = ONE.test_id and testRollback.name = ONE.test_name ", joinCondition);
        System.out.println(joinCondition);
    }
}