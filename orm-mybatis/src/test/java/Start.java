import com.czy.core.orm.entity.TestEntity;
import com.czy.core.orm.mapper.MySqlMapper;
import com.czy.core.orm.base.QueryParams;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by panlc on 2017-03-13.
 */
public class Start {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"});
        applicationContext.start();
        try {
            System.out.println(applicationContext.getBean("datasource-default"));
            System.out.println(applicationContext.getBean("datasource-ds1"));

            MySqlMapper bean = applicationContext.getBean(MySqlMapper.class);

            TestEntity testEntity = new TestEntity();
            testEntity.setName("tsetest");
            long insert = bean.insert(testEntity);
            bean.deleteByPrimaryKey(testEntity.getId());
            System.out.println(insert);
            testEntity.setId(12L);
            testEntity.setName("updated");
            int i = bean.updateByPrimaryKey(testEntity);
            int i1 = bean.updateByPrimaryKeySelective(testEntity);
            QueryParams queryParams = new QueryParams(TestEntity.class);
            queryParams.selectProperties("name");
            QueryParams.Criteria criteria = queryParams.createCriteria();
            criteria.andLike("name", "et");
            List<TestEntity> testEntities = bean.selectListByParams(queryParams);
            bean.deleteByParams(queryParams);
            System.out.println(testEntities);

            System.out.println(i);
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
