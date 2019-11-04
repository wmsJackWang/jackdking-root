package springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import springboot.bean.User;
import springboot.dao.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDatasourceMethodApplicationTests {

	@Test
	public void contextLoads() {
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