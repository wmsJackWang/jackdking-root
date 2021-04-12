package dache.test;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import dache.entity.TripOrder;
import dache.service.DriverService;
import dache.util.DaCheUtil;

@Component
public class DriverRunner implements ApplicationRunner{

	@Autowired
	DriverService driverService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		int latitude , longtude;
		TripOrder tripOrder ;
		int userid=0;
		
		while(true) {
			//随机获取乘客位置
			latitude = DaCheUtil.getLatitu_lontude(DaCheUtil.lati-100, DaCheUtil.lati+100);
			longtude = DaCheUtil.getLatitu_lontude(DaCheUtil.lontu-100, DaCheUtil.lontu+100);
			
			tripOrder = driverService.getOrderInfo(latitude, longtude);
			if(tripOrder!=null)
			{
				userid++;
				System.out.println("司机"+userid+"抢到订单："+tripOrder.toString());
			}else {
				System.out.println("司机"+userid+"未抢到订单");
			}
			
			TimeUnit.SECONDS.sleep(2);
		}
		
		
	}

}
