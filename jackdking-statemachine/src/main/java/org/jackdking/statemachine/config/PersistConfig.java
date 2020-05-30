package org.jackdking.statemachine.config;
import org.jackdking.statemachine.bean.Order;
import org.jackdking.statemachine.enums.OrderEvents;
import org.jackdking.statemachine.enums.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.redis.RedisStateMachinePersister;

@Configuration
public class PersistConfig {
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private InMemoryStateMachinePersist inMemoryStateMachinePersist;
	
	@Autowired
    private OrderStateMachinePersist orderStateMachinePersist;
	
	/**
	 * 注入StateMachinePersister对象
	 * 
	 * @return
	 */
	@Bean(name="orderMemoryPersister")
	public StateMachinePersister<OrderStates, OrderEvents, String> getPersister() {
		return new DefaultStateMachinePersister<>(inMemoryStateMachinePersist);
	}
	
	/**
	 * 注入RedisStateMachinePersister对象
	 * 
	 * @return
	 */
	@Bean(name = "orderRedisPersister")
	public RedisStateMachinePersister<OrderStates, OrderEvents> redisPersister() {
		return new RedisStateMachinePersister<>(redisPersist());
	}

	/**
	 * 通过redisConnectionFactory创建StateMachinePersist
	 * 
	 * @return
	 */
	public StateMachinePersist<OrderStates, OrderEvents,String> redisPersist() {
		RedisStateMachineContextRepository<OrderStates, OrderEvents> repository = new RedisStateMachineContextRepository<>(redisConnectionFactory);
		return new RepositoryStateMachinePersist<>(repository);
	}
	
	@Bean(name="orderPersister")
    public StateMachinePersister<OrderStates, OrderEvents, Order> orderPersister() {
		return new DefaultStateMachinePersister<OrderStates, OrderEvents, Order>(orderStateMachinePersist);
	}

}