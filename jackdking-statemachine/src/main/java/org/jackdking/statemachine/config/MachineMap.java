package org.jackdking.statemachine.config;

import java.util.HashMap;
import java.util.Map;

import org.jackdking.statemachine.enums.FormEvents;
import org.jackdking.statemachine.enums.FormStates;
import org.jackdking.statemachine.enums.OrderEvents;
import org.jackdking.statemachine.enums.OrderStates;
import org.springframework.statemachine.StateMachine;

/**存放
 * @author wphmo
 *
 */
public class MachineMap {
	public static Map<String,StateMachine<OrderStates, OrderEvents>> orderMap = new HashMap<String,StateMachine<OrderStates, OrderEvents>>();
	public static Map<String,StateMachine<FormStates, FormEvents>> formMap = new HashMap<String,StateMachine<FormStates, FormEvents>>();

}
