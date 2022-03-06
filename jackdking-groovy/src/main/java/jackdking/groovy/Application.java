package jackdking.groovy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * groovy基础入门教程
 * https://blog.csdn.net/kmyhy/article/details/4200563
 *
 * groovy将脚本类 java方法  编译成class 并注入到IOC后，才能
 * https://blog.csdn.net/qq_29698805/article/details/104639140
 *
 * groovy脚本开发
 * @author YI
 * @date 2018-12-6 09:22:42
 *
 * 用法一： 将脚本类注入到ioc， 然后再调用bean并执行。
 * https://www.cnblogs.com/plumswine/p/14238816.html
 *
 * 用法二： 使用binding技术，动态绑定IOC中的service bean，脚本直接调用beanname.method。
 * https://blog.csdn.net/qq_29698805/article/details/104639140
 *
 * testgroovy包下: 直接new 脚本类 或者 classloader解析脚本类内容并newinstance创建脚本类对象。
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
