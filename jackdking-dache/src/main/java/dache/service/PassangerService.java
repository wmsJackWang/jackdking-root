package dache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dache.entity.TripOrder;
import dache.util.DaCheUtil;
import dache.util.RedisOperator;

/*
 * 司机发布行程单服务类
 */
@Service
public class PassangerService {
	
    @Autowired
    public RedisOperator redis;
	
	public void pushTripOrder(TripOrder order) {
		
		
		String netPointKey = DaCheUtil.getNetPointKey(order.getLatitude(), order.getLongtude());
		
		redis.lpush(netPointKey, JSON.toJSONString(order));
		
		
	}

}
