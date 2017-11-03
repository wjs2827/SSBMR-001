package com.test.springboot.config;

import java.util.Date;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.test.springboot.util.RedisUtil;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redis缓存配置加载
 * @author jinshan.wang.it
 *
 */
@Configuration
public class RedisConfig {
	
	private static final Log log=LogFactory.getLog(JdbcConfig.class); 
	
//	@Value("${redis.hostName}")
//	private String hostName;
//	
//	@Value("${redis.port}")
//	private Integer port;
//	
//	@Value("${redis.password}")
//	private String password;
	
	/**
	 * JedisPoolConfig 连接池
	 * @return
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig(){
		log.info(new Date()+"##################redis连接池配置#############################");
		JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
		//最大空闲数
		jedisPoolConfig.setMaxIdle(300);
		//连接池的最大数据库连接数
		jedisPoolConfig.setMaxTotal(1000);
		//最大建立连接等待时间
		jedisPoolConfig.setMaxWaitMillis(1000);
		//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		jedisPoolConfig.setMinEvictableIdleTimeMillis(300000);
		//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		jedisPoolConfig.setNumTestsPerEvictionRun(10);
		//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
		//是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
		jedisPoolConfig.setTestOnBorrow(true);
		//在空闲时检查有效性, 默认false
		jedisPoolConfig.setTestWhileIdle(true);
		return jedisPoolConfig;
	}
	
	/**
	 * 配置工厂
	 * 配置redis端口ip信息方式
	 * @param jedisPoolConfig
	 * @return
	 * 第一种方式：注解@ConfigurationProperties(prefix = "redis")//指定数据源的前缀,在application.properties文件中指定
	 * #配置redis
	 * redis.hostName=*********
	 * return new JedisConnectionFactory();自动注解配置
	 * 第二种方式：
	 * JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
     * //连接池
	 * jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
	 * //IP地址
	 * jedisConnectionFactory.setHostName(hostName);
	 * //端口号
	 * jedisConnectionFactory.setPort(port);
	 * //如果Redis设置有密码
	 * jedisConnectionFactory.setPassword(password);
	 * //客户端超时时间单位是毫秒
	 * jedisConnectionFactory.setTimeout(5000);
	 * return jedisConnectionFactory;
	 */
	@Bean
	@ConfigurationProperties(prefix = "redis")//指定数据源的前缀,在application.properties文件中指定
	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
		log.info(new Date()+"##################redis指定数据源#############################");
		return new JedisConnectionFactory();
//		JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
//		//连接池
//		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
//		//IP地址
//		jedisConnectionFactory.setHostName(hostName);
//		//端口号
//		jedisConnectionFactory.setPort(port);
//		//如果Redis设置有密码
//		jedisConnectionFactory.setPassword(password);
//		//客户端超时时间单位是毫秒
//		jedisConnectionFactory.setTimeout(5000);
//		return jedisConnectionFactory;
	}

	/**
     * 实例化 RedisTemplate 对象
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> functionDomainRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
    	log.info(new Date()+"##################实例化 RedisTemplate 对象#############################");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        initDomainRedisTemplate(redisTemplate, jedisConnectionFactory);
        return redisTemplate;
    }
    
    /**
     * 设置数据存入 redis 的序列化方式,并开启事务
     * @param redisTemplate
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
    }
    
    @Bean(name="redisUtil")
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate){
    	log.info(new Date()+"##################redisUtil工具#############################");
    	RedisUtil redisUtil=new RedisUtil();
    	redisUtil.setRedisTemplate(redisTemplate);
    	return redisUtil;
    }
	
}
