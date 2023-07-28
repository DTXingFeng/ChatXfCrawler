package xyz.xingfeng.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("xyz.xingfeng.service")
@PropertySource("classpath:db.properties")
@Import({jdbcConfig.class,MyBatisConfig.class})
//开启注解式事物管理
@EnableTransactionManagement
public class SpringConfig {

}
