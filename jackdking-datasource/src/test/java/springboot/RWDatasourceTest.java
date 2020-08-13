package springboot;

import org.jackdking.core.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import springboot.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RWDatasourceTest {
	
	@Autowired
	private UserService userService;
	
	//write
	@Test
	public void write(){
		System.out.println("write insert");
		User user = new User();
		user.setId(12);
		user.setName("jackdking-write");
		user.setGender("male");
		userService.insert2Slave(user);
		System.out.println("write insert succeeded");
	}
	
	//读操作
	@Test
	public void testSelect() {
		userService.select(); 
	}
	
}
