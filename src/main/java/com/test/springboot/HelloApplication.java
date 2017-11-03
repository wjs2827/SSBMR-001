package com.test.springboot;

import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.test.springboot.config.JdbcConfig;

@SpringBootApplication
@PropertySource(value={"classpath:config/${spring.profiles.active}/application-${spring.profiles.active}.properties"},ignoreResourceNotFound=true,encoding="utf-8")
public class HelloApplication{
	
	private static final Log log=LogFactory.getLog(JdbcConfig.class); 
	
	public static void main(String[] args) {
		log.info(new Date()+"####################### spring boot 开始启动   ##########################");
		SpringApplication.run(HelloApplication.class, args);
	}
	
}