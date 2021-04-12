package dache.test;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import dache.entity.TripOrder;
import dache.service.PassangerService;
import dache.util.DaCheUtil;

@Component
public class PassageerRunner  implements ApplicationRunner{


	@Autowired
	PassangerService passangerService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("..............");
		int latitude , longtude;
		TripOrder tripOrder ;
		int userid=0;
		
		while(true) {
			//随机获取乘客位置
			latitude = DaCheUtil.getLatitu_lontude(DaCheUtil.lati-100, DaCheUtil.lati+100);
			longtude = DaCheUtil.getLatitu_lontude(DaCheUtil.lontu-100, DaCheUtil.lontu+100);
			
			tripOrder = new TripOrder();
			tripOrder.setLatitude(latitude);
			tripOrder.setLongtude(longtude);
			tripOrder.setUserName(userid+"");
			tripOrder.setDeparture("海淀黄庄");
			tripOrder.setDestination("西直门");
			passangerService.pushTripOrder(tripOrder);
			userid++;
			System.out.println("发出的订单："+tripOrder.toString());
			
			TimeUnit.SECONDS.sleep(5);
		}
		
		
	}

}
