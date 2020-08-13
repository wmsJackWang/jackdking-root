package springboot;

import java.io.IOException;

import org.jackdking.core.bean.User;
import org.jackdking.core.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.test.context.junit4.SpringRunner;

import springboot.service.UserService;

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

	@Autowired
	private UserService userService;
	


	@Test
	public void testSelect(){
		User user = userMapper.selectByPrimaryKey(11);
		System.out.println(user.getId() + "--" + user.getName() + "==" + user.getGender());
	}

	/*
	 * 测试插入master库
	 */
	@Test
	public void testInsert2Master(){
		System.out.println("Master insert");
		User user = new User();
		user.setId(11);
		user.setName("jackdking-master");
		user.setGender("male");
		userService.insert2Master(user);
		System.out.println("Master insert succeeded");
	}
	
	/*
	 * 测试插入slave库
	 */
	@Test
	public void testInsert2Slave(){
		System.out.println("Slave insert");
		User user = new User();
		user.setId(11);
		user.setName("jackdking-slave");
		user.setGender("male");
		userService.insert2Slave(user);
		System.out.println("Slave insert succeeded");
	}
	
	
}