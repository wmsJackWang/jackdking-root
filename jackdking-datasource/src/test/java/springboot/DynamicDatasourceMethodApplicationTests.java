package springboot;

import java.io.IOException;

import org.jackdking.core.bean.User;
import org.jackdking.core.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDatasourceMethodApplicationTests {

	@Test
	public void contextLoads() {
		try {
	        Resource[] resources = new PathMatchingResourcePatternResolver()
	                .getResources("classpath*:mapper/*Mapper.xml");
	        System.out.println(resources[0].getFilename());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Autowired
	private UserMapper userMapper;
	


	@Test
	public void testSelect(){
		User user = userMapper.selectByPrimaryKey(10);
		System.out.println(user.getId() + "--" + user.getName() + "==" + user.getGender());
	}

	@Test
	public void testInsert(){
		User user = new User();
		user.setId(11);
		user.setName("jackdking-master");
		user.setGender("male-master");
		userMapper.insert(user);
		System.out.println("insert succeeded");
	}
}