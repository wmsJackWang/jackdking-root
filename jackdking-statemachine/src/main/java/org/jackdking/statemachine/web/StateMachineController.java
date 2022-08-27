package org.jackdking.statemachine.web;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.jackdking.statemachine.bean.Form;
import org.jackdking.statemachine.bean.Order;
import org.jackdking.statemachine.complexform.ComplexFormStateMachineBuilder;
import org.jackdking.statemachine.config.InMemoryStateMachinePersist;
import org.jackdking.statemachine.config.MachineMap;
import org.jackdking.statemachine.enums.ComplexFormEvents;
import org.jackdking.statemachine.enums.ComplexFormStates;
import org.jackdking.statemachine.enums.FormEvents;
import org.jackdking.statemachine.enums.FormStates;
import org.jackdking.statemachine.enums.OrderEvents;
import org.jackdking.statemachine.enums.OrderStates;
import org.jackdking.statemachine.form.FormStateMachineBuilder;
import org.jackdking.statemachine.myfactory.OrderStateMachineBuilder;
import org.jackdking.statemachine.springfactory.StateMachineFactoryConfig;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineAccessor;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statemachine")
public class StateMachineController {
	
	
	//状态机对象是有状态的对象，每次状态流转结束了，是还会留存上一次状态信息的。
	@Autowired
	private StateMachine orderSingleMachine;
	
	@Autowired
	private OrderStateMachineBuilder orderStateMachineBuilder;
	
	@Autowired
	@Qualifier("orderMachineFactory")
	private StateMachineFactory<OrderStates, OrderEvents> orderMachineFactory;
	
	@Autowired
	private FormStateMachineBuilder formStateMachineBuilder;
	
	@Autowired
	private ComplexFormStateMachineBuilder complexFormStateMachineBuilder;
	
	@Autowired
	private InMemoryStateMachinePersist inMemoryStateMachinePersist;
	
	@Resource(name="orderMemoryPersister")
	private StateMachinePersister<OrderStates, OrderEvents, String> orderMemorypersister;
	
	@Resource(name="orderRedisPersister")
	private StateMachinePersister<OrderStates, OrderEvents, String> orderRedisPersister;
	
	@Resource(name="orderPersister")
	private StateMachinePersister<OrderStates, OrderEvents, Order> persister;

	@Autowired
	private BeanFactory beanFactory;
	
	@RequestMapping("/testSingleOrderState")
	public void testSingleOrderState() throws Exception {

		// 创建流程
		orderSingleMachine.start();
		
		TimeUnit.SECONDS.sleep(3);
		// 触发PAY事件
		orderSingleMachine.sendEvent(OrderEvents.PAY);

		TimeUnit.SECONDS.sleep(3);
		// 触发RECEIVE事件
		orderSingleMachine.sendEvent(OrderEvents.RECEIVE);
		TimeUnit.SECONDS.sleep(3);
		

//		Order order = new Order(orderId, "547568678", "广东省深圳市", "13435465465", "RECEIVE");
//		Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.RECEIVE).setHeader("order", order).build();
//		stateMachine.sendEvent(message);

		// 获取最终状态
		System.out.println("最终状态：" + orderSingleMachine.getState().getId());
	}
	
	
	@RequestMapping("/testOrderState")
	public void testOrderState(String orderId) throws Exception {

		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		System.out.println(stateMachine.getId());

		// 创建流程
		stateMachine.start();

		// 触发PAY事件
		stateMachine.sendEvent(OrderEvents.PAY);

		// 触发RECEIVE事件
		//stateMachine.sendEvent(OrderEvents.RECEIVE);
		
		//用message传递数据
		Order order = new Order(orderId, "547568678", "广东省深圳市", "13435465465", "RECEIVE");
		Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.RECEIVE).setHeader("order", order).setHeader("otherObj", "otherObjValue").build();
		stateMachine.sendEvent(message);

		// 获取最终状态
		System.out.println("最终状态：" + stateMachine.getState().getId());
	}
	
	
	@RequestMapping("/testOrderStateFactory")
	public void testOrderStateFactory(String orderId) throws Exception {

		StateMachine<OrderStates, OrderEvents> stateMachine = orderMachineFactory.getStateMachine(StateMachineFactoryConfig.orderStateMachineId);
		System.out.println(stateMachine.getId());

		// 创建流程
		stateMachine.start();

		// 触发PAY事件
		stateMachine.sendEvent(OrderEvents.PAY);

		// 触发RECEIVE事件
		//stateMachine.sendEvent(OrderEvents.RECEIVE);
		
		//用message传递数据
		Order order = new Order(orderId, "547568678", "广东省深圳市", "13435465465", "RECEIVE");
		Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.RECEIVE).setHeader("order", order).setHeader("otherObj", "otherObjValue").build();
		stateMachine.sendEvent(message);

		// 获取最终状态
		System.out.println("最终状态：" + stateMachine.getState().getId());
		
		

		stateMachine = orderMachineFactory.getStateMachine(StateMachineFactoryConfig.otherOrderStateFactoryMachineId);
		System.out.println(stateMachine.getId());

		// 创建流程
		stateMachine.start();

		// 触发PAY事件
		stateMachine.sendEvent(OrderEvents.PAY);

		// 触发RECEIVE事件
		//stateMachine.sendEvent(OrderEvents.RECEIVE);
		
		//用message传递数据
		order = new Order(orderId, "547568678", "广东省深圳市", "13435465465", "RECEIVE");
		message = MessageBuilder.withPayload(OrderEvents.RECEIVE).setHeader("order", order).setHeader("otherObj", "otherObjValue").build();
		stateMachine.sendEvent(message);

		// 获取最终状态
		System.out.println("最终状态：" + stateMachine.getState().getId());
	}
	
	@RequestMapping("/testFormState")
	public void testFormState() throws Exception {

		StateMachine<FormStates, FormEvents> stateMachine = formStateMachineBuilder.build(beanFactory);
		System.out.println(stateMachine.getId());

		// 创建流程
		stateMachine.start();

		stateMachine.sendEvent(FormEvents.WRITE);

		stateMachine.sendEvent(FormEvents.CONFIRM);

		stateMachine.sendEvent(FormEvents.SUBMIT);

		// 获取最终状态
		System.out.println("最终状态：" + stateMachine.getState().getId());
	}
	
	@RequestMapping("/testComplexFormState")
	public void testComplexFormState() throws Exception {

		StateMachine<ComplexFormStates, ComplexFormEvents> stateMachine = complexFormStateMachineBuilder.build(beanFactory);
		System.out.println(stateMachine.getId());
		
		Form form1 = new Form();
		form1.setId("111");
		form1.setFormName(null);
		
		Form form2 = new Form();
		form2.setId("222");
		form2.setFormName("好的表单");
		
		Form form3 = new Form();
		form3.setId("333");
		form3.setFormName(null);

		// 创建流程
		System.out.println("-------------------form1------------------");
		
		stateMachine.start();
		Message message = MessageBuilder.withPayload(ComplexFormEvents.WRITE).setHeader("form", form1).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		message = MessageBuilder.withPayload(ComplexFormEvents.CHECK).setHeader("form", form1).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		message = MessageBuilder.withPayload(ComplexFormEvents.DEAL).setHeader("form", form1).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		message = MessageBuilder.withPayload(ComplexFormEvents.SUBMIT).setHeader("form", form1).build();
		stateMachine.sendEvent(message);
		System.out.println("最终状态：" + stateMachine.getState().getId());

		StateMachineAccessor accessor = stateMachine.getStateMachineAccessor();
		List<StateMachineAccess<OrderStates, OrderEvents>> withAllRegions = accessor.withAllRegions();


		
		System.out.println("-------------------form2------------------");
		stateMachine = complexFormStateMachineBuilder.build(beanFactory);
		stateMachine.start();
		message = MessageBuilder.withPayload(ComplexFormEvents.WRITE).setHeader("form", form2).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		message = MessageBuilder.withPayload(ComplexFormEvents.CHECK).setHeader("form", form2).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		message = MessageBuilder.withPayload(ComplexFormEvents.DEAL).setHeader("form", form2).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		message = MessageBuilder.withPayload(ComplexFormEvents.SUBMIT).setHeader("form", form2).build();
		stateMachine.sendEvent(message);
		System.out.println("最终状态：" + stateMachine.getState().getId());
		
		System.out.println("-------------------form3------------------");
		stateMachine = complexFormStateMachineBuilder.build(beanFactory);
		stateMachine.start();
		message = MessageBuilder.withPayload(ComplexFormEvents.WRITE).setHeader("form", form3).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		message = MessageBuilder.withPayload(ComplexFormEvents.CHECK).setHeader("form", form3).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		form3.setFormName("好的表单");
		message = MessageBuilder.withPayload(ComplexFormEvents.DEAL).setHeader("form", form3).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		message = MessageBuilder.withPayload(ComplexFormEvents.SUBMIT).setHeader("form", form3).build();
		stateMachine.sendEvent(message);
		message = MessageBuilder.withPayload(ComplexFormEvents.CHECK).setHeader("form", form3).build();
		stateMachine.sendEvent(message);
		System.out.println("当前状态：" + stateMachine.getState().getId());
		message = MessageBuilder.withPayload(ComplexFormEvents.SUBMIT).setHeader("form", form3).build();
		stateMachine.sendEvent(message);
		System.out.println("最终状态：" + stateMachine.getState().getId());
	}
	
	@RequestMapping("/sendEvent")
    void sendEvent(String machineId,String events,String id) throws Exception{
		if(machineId.equals("form")) {
			StateMachine sm = MachineMap.formMap.get(id);
			Form form = new Form();
			form.setId(id);
			if(sm == null) {
				if(events.equals("WRITE")) {
					sm = formStateMachineBuilder.build(beanFactory);
					sm.start();
					MachineMap.formMap.put(id, sm);
				}else {
					System.out.println("该表单流程尚未开始，不能做"+events+"转换");
					return;
				}
			}
			
			Message message = MessageBuilder.withPayload(FormEvents.valueOf(events)).setHeader("form", form).build();
			sm.sendEvent(message);
		}
		if(machineId.equals("order")) {
			StateMachine sm = MachineMap.orderMap.get(id);
			Order order = new Order();
			order.setId(id);
			if(sm == null) {
				if(events.equals("PAY")) {
					sm = orderStateMachineBuilder.build(beanFactory);
					sm.start();
					MachineMap.orderMap.put(id, sm);
				}else {
					System.out.println("该订单流程尚未开始，不能做"+events+"转换");
					return;
				}
				
			}
			Message message = MessageBuilder.withPayload(OrderEvents.valueOf(events)).setHeader("order", order).setHeader("test","test1").build();
			sm.sendEvent(message);
		}
    }
	
	@RequestMapping("/testMemoryPersister")
	public void tesMemorytPersister(String id) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		stateMachine.start();
		
		//发送PAY事件
		stateMachine.sendEvent(OrderEvents.PAY);
		Order order = new Order();
		order.setId(id);
		
		//持久化stateMachine
		orderMemorypersister.persist(stateMachine, order.getId());
	
	}
	
	@RequestMapping("/testMemoryPersisterRestore")
	public void testMemoryRestore(String id) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		orderMemorypersister.restore(stateMachine, id);
		System.out.println("恢复状态机后的状态为：" + stateMachine.getState().getId());
	}
	
	@RequestMapping("/testRedisPersister")
	public void testRedisPersister(String id) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		stateMachine.start();
		Order order = new Order();
		order.setId(id);
		//发送PAY事件
		Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.PAY).setHeader("order", order).build();
		stateMachine.sendEvent(message);
		//持久化stateMachine
		orderRedisPersister.persist(stateMachine, order.getId());
	}
	
	@RequestMapping("/testRedisPersisterRestore")
	public void testRestore(String id) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		orderRedisPersister.restore(stateMachine, id);
		System.out.println("恢复状态机后的状态为：" + stateMachine.getState().getId());
	}
	
	@RequestMapping("/testOrderRestore")
	public void testOrderRestore(String id) throws Exception {
		StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
		//订单
		Order order = new Order();
		order.setId(id);
		order.setState(OrderStates.WAITING_FOR_RECEIVE.toString());
		//恢复
		persister.restore(stateMachine, order);
		//查看恢复后状态机的状态
		System.out.println("恢复后的状态：" + stateMachine.getState().getId());
	}
}
