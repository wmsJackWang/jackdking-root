package dache.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DriverRunner implements ApplicationRunner{

	@Autowired
	private CommonTestService commonTestService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("begin");
		commonTestService.doDriverBussiness();
		System.out.println("return");
	
		}


}
