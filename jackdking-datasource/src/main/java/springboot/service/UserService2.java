package springboot.service;

import org.jackdking.core.bean.User;
import org.jackdking.core.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.MySolution_RWseparation.mybatis.dynamicdatasource.DataSourceType;
import springboot.MySolution_dynamicdatasource.annotation.DBType;
import springboot.MySolution_dynamicdatasource.proxyautowired.SelfBeanProxyAware;

@Service
public class UserService2 implements SelfBeanProxyAware{

    @Autowired
    private UserMapper userMapper;
    
    private UserService2 userServiceProxy;

    @DBType(DataSourceType.SLAVE)
    public void select(){
        User user = userMapper.selectByPrimaryKey(2);
        System.out.println(user.getId() + "--" + user.getName() + "==" + user.getGender());
    }

    @DBType(DataSourceType.MASTER)
    public void select1() {
        User user = userMapper.selectByPrimaryKey(10);
        System.out.println("MASTER:"+user.getId() + "--" + user.getName() + "==" + user.getGender());
        select2();
    } 

    @DBType(DataSourceType.SLAVE)
    public void select2() {
    	User user = userMapper.selectByPrimaryKey(10);
        System.out.println("SLAVE:"+user.getId() + "--" + user.getName() + "==" + user.getGender());
    }
    
    @DBType(DataSourceType.MASTER)
    public void select3() {
        User user = userMapper.selectByPrimaryKey(10);
        System.out.println("MASTER:"+user.getId() + "--" + user.getName() + "==" + user.getGender());
        userServiceProxy.select2();
    } 
 
    

    @DBType(DataSourceType.MASTER)
    public void insert(User user){
        userMapper.insertSelective(user);
    }

	@Override
	public void setSelBeanfProxy(Object proxyObj) {
		// TODO Auto-generated method stub
		this.userServiceProxy = (UserService2)proxyObj;
	}
}