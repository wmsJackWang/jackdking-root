package springboot.MySolution_RWseparation.mybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


/**
 * @author mingshengwang
 * @date 2019年10月29日 下午4:50:32
 * @todo TODO
 * @descript null
 */
@Configuration 
public class MyBatisConfig {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeAliasesPackage("springboot.bean");
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*Mapper.xml");
        sessionFactoryBean.setMapperLocations(resources);

        return sessionFactoryBean.getObject();
    }
}