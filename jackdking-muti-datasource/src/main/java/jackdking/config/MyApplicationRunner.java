package jackdking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import jackdking.bean.User;
import jackdking.dao.db1.User1Mapper;
import jackdking.dao.db2.User2Mapper;

@Component
public class MyApplicationRunner implements ApplicationRunner{
	
	@Autowired
	User1Mapper  user1Mapper;

	@Autowired
	User2Mapper  user2Mapper;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("从db1数据库中查询数据");
		
		User user1 = user1Mapper.selectByPrimaryKey(11);
		System.out.println(user1.toString());

		System.out.println("从db2数据库中查询数据");
		
		
		User user2 = user2Mapper.selectByPrimaryKey(11);
		System.out.println(user2.toString());

		
	}

}
