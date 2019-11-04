package springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import springboot.bean.User;
import springboot.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDatasourceAnnoApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
	}

//	@Test
//	public void testSelect(){
//		userService.select();
//	}

	/**
	 * @author mingshengwang
	 * @date 2019年11月1日
	 * @return void
	 * @parameter null
	 * @description 测试内部函数之间调用是否能切换数据源成功
	 * 测试结果是：
	 * 		MASTER:10--jackdking-master==male-master
	 * 		SLAVE:10--jackdking-master==male-master
	 * 	结论： 内部方法之间数据源切换失败
	 */
	@Test
	public void testSelect1() {
		userService.select1(); 
	}
	
	/**
	 * @author mingshengwang
	 * @date 2019年11月1日
	 * @return void
	 * @parameter null
	 * @description 使用获取代理对象的方式就能解决 注解失效的问题
	 * 结果：	
	 * 			
	 */
	@Test
	public void testSelect3() {
		userService.select3(); 
	}
//	
//	@Test
//	public void testInsert(){
//		User user = new User();
//		user.setId(2);
//		user.setName("wjy");
//		user.setGender("female");
//		userService.insert(user);
//	}
}