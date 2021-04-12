package dache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import dache.entity.TripOrder;
import dache.util.DaCheUtil;
import dache.util.RedisOperator;

/*
 * 司机抢单 服务类
 */
@Service
public class DriverService {

    @Autowired
    public RedisOperator redis;
	
	public TripOrder getOrderInfo(int latitude , int longitude) {
		
		TripOrder tripOrder = null;

		String netPointKey = DaCheUtil.getNetPointKey(latitude , longitude);
		
		String result = redis.rpop(netPointKey);
		
		if(result!=null)
			tripOrder = JSON.parseObject(result, TripOrder.class);
			
		return tripOrder;
	}

}
