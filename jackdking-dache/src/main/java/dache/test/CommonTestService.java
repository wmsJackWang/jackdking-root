package dache.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import dache.entity.TripOrder;
import dache.service.DriverService;
import dache.service.PassangerService;
import dache.util.DaCheUtil;

@Service
public class CommonTestService {
	

	@Autowired
	DriverService driverService;
	

	@Autowired
	PassangerService passangerService;

	@Async
	public void doPassageerBussiness() throws InterruptedException {
		
		int latitude , longtude;
		TripOrder tripOrder ;
		int userid=0;
		
		while(true) {
			//随机获取乘客位置, 在（lati,lontu）点的100''区间内随机生成一个用户行程单
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
			
			TimeUnit.MILLISECONDS.sleep(50*1);
		}
		
	}
	
	
	@Async
	public void doDriverBussiness() throws InterruptedException {

		
		ThreadPoolExecutor excutor = new ThreadPoolExecutor(2, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));

		int id = 0;
		
		while(true) {//循环随机在固定的这些区域，出现特定的司机
			
			Driver dirver = new Driver(id++, driverService);
			excutor.submit(dirver);
			TimeUnit.MILLISECONDS.sleep(100);
			
		}

	}
}

class Driver implements  Runnable{

	int userid ;
	DriverService driverService;
	
	public Driver(int id , DriverService driverService){
		this.userid =id;
		this.driverService = driverService;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int latitude , longtude;
		TripOrder tripOrder ;
		while(true) {
			//随机获取乘客位置
			latitude = DaCheUtil.getLatitu_lontude(DaCheUtil.lati-100, DaCheUtil.lati+100);
			longtude = DaCheUtil.getLatitu_lontude(DaCheUtil.lontu-100, DaCheUtil.lontu+100);
			
			tripOrder = driverService.getOrderInfo(latitude, longtude);
			if(tripOrder!=null)
			{
				System.out.println("司机"+userid+"抢到订单："+tripOrder.toString());
				break;
			}else {
				System.out.println("司机"+userid+"未抢到订单");
			}
			
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
}
