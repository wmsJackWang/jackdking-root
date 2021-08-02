package annotation.reflection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.Size;
import java.lang.reflect.Method;

@Service
public class ReInvokeService {

    @Resource
    private SpringContextsUtil springContextsUtil;

    public void reInvoke(String beanName,String methodName,String[] params){
        Method method = ReflectionUtils.findMethod(springContextsUtil.getBean(beanName).getClass(), methodName, String.class);
        if(method == null) {
            System.out.println("方法对象不能为空");
            return;
        }
        Object[] param1 = new Object[1];
        param1[0]=params[0];
        ReflectionUtils.invokeMethod(method, springContextsUtil.getBean(beanName), param1);
    }

    public void print(String info){
        System.out.println(info);
    }
}