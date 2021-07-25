package dache.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PassageerRunner implements ApplicationRunner{

	@Autowired
	private CommonTestService commonTestService;


	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub

		commonTestService.doPassageerBussiness();
	}

}
