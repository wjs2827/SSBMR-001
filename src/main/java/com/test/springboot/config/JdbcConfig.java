package com.test.springboot.config;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
//import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.test.springboot.util.Base64Util;

/**
 * 数据源配置
 * @author jinshan.wang.it
 *
 */
@Configuration
public class JdbcConfig {
	
	private static final Log log=LogFactory.getLog(JdbcConfig.class); 
	
//	@Value("${jdbc.driver}")
//	private String driver;
//	
//	@Value("${jdbc.url}")
//	private String url;
//	
//	@Value("${jdbc.username}")
//	private String username;
//	
//	@Value("${jdbc.password}")
//	private String password;
//	
	@Value("${druid.username}")
	private String druidUsername;
	
	@Value("${druid.password}")
	private String druidPassword;
	
	/**
	 * 配置druid数据源
	 * 配置redis端口ip信息方式
	 * @param jedisPoolConfig
	 * @return
	 * 第一种方式：注解@ConfigurationProperties(prefix = "spring.datasource")//指定数据源的前缀,在application.properties文件中指定
	 * #配置jdbc数据源
	 * spring.datasource.username=*********
	 * return new DruidDataSource();自动注解配置
	 * 第二种方式：
	 * 下列方法注掉的代码，driver等信息通过上面注解获取
	 * @Value("${jdbc.driver}")
     * private String driver;
	 */
	@Bean(name="dataSource",destroyMethod="close")
	@ConfigurationProperties(prefix = "spring.datasource")//指定数据源的前缀,在application.properties文件中指定
	public DruidDataSource dataSource() throws SQLException{
		log.info(new Date()+"##################数据源启动加载开始#############################");
		return new DruidDataSource();
//		DruidDataSource dataSource = new DruidDataSource();
//		dataSource.setDriverClassName(driver);
//		dataSource.setUrl(url);
//		dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        //配置最大连接
//        dataSource.setMaxActive(300);
//        //配置初始连接
//        dataSource.setInitialSize(20);
//        //配置最小连接
//        dataSource.setMinIdle(10);
//        //连接等待超时时间
//        dataSource.setMaxWait(60000);
//        //间隔多久进行检测,关闭空闲连接
//        dataSource.setTimeBetweenEvictionRunsMillis(60000);
//        //一个连接最小生存时间
//        dataSource.setMinEvictableIdleTimeMillis(300000);
//        //连接等待超时时间 单位为毫秒 缺省启用公平锁，
//        //并发效率会有所下降， 如果需要可以通过配置useUnfairLock属性为true使用非公平锁
//        dataSource.setUseUnfairLock(true);
//        //用来检测是否有效的sql
//        dataSource.setValidationQuery("select 'x'");
//        dataSource.setTestWhileIdle(true);
//        //申请连接时执行validationQuery检测连接是否有效，配置为true会降低性能
//        dataSource.setTestOnBorrow(false);
//        //归还连接时执行validationQuery检测连接是否有效，配置为true会降低性能
//        dataSource.setTestOnReturn(false);
//        //打开PSCache,并指定每个连接的PSCache大小启用poolPreparedStatements后，
//        //PreparedStatements 和CallableStatements 都会被缓存起来复用，
//        //即相同逻辑的SQL可以复用一个游标，这样可以减少创建游标的数量。
//        dataSource.setPoolPreparedStatements(true);
//        dataSource.setMaxOpenPreparedStatements(20);
//        //配置sql监控的filter
//        dataSource.setFilters("stat,wall,log4j");
//        try {
//            dataSource.init();
//        } catch (SQLException e) {
//            throw new RuntimeException("druid datasource init fail");
//        }
//        return dataSource;
	}
	
	/**
	 * 创建SqlSessionFactory 并加载mapper接口对应的xml
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		log.info("###############创建SqlSessionFactory#############################");
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	/**
     * druid监控
     * 监控访问地址 http://localhost/druid/index.html
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
    	log.info(new Date()+"##################druid监控加载开始#############################");
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("loginUsername", druidUsername);
        reg.addInitParameter("loginPassword", druidPassword);
        return reg;
    }

    /**
     * druid监控过滤
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
    	log.info(new Date()+"##################druid监控过滤加载开始#############################");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
    
	@Bean
	public String fzby(){
		log.info(new Date()+"##################fzby#############################");
		String fozuStr = "ICAgICAgICAgICAgICAgICAgIF9vb09vb18KICAgICAgICAgICAgICAgICAgbzg4ODg4ODhvCiAgICAgICAgICAgICAgICAgIDg4IiAuICI4OAogICAgICAgICAgICAgICAgICAofCAtXy0gfCkKICAgICAgICAgICAgICAgICAgT1wgID0gIC9PCiAgICAgICAgICAgICAgIF9fX18vYC0tLSdcX19fXwogICAgICAgICAgICAgLicgIFxcfCAgICAgfC8vICBgLgogICAgICAgICAgICAvICBcXHx8fCAgOiAgfHx8Ly8gIFwKICAgICAgICAgICAvICBffHx8fHwgLTotIHx8fHx8LSAgXAogICAgICAgICAgIHwgICB8IFxcXCAgLSAgLy8vIHwgICB8CiAgICAgICAgICAgfCBcX3wgICcnXC0tLS8nJyAgfCAgIHwKICAgICAgICAgICBcICAuLVxfXyAgYC1gICBfX18vLS4gLwogICAgICAgICBfX19gLiAuJyAgLy0tLi0tXCAgYC4gLiBfXwogICAgICAuIiIgJzwgIGAuX19fXF88fD5fL19fXy4nICA+JyIiLgogICAgIHwgfCA6ICBgLSBcYC47YFwgXyAvYDsuYC8gLSBgIDogfCB8CiAgICAgXCAgXCBgLS4gICBcXyBfX1wgL19fIF8vICAgLi1gIC8gIC8KPT09PT09YC0uX19fX2AtLl9fX1xfX19fXy9fX18uLWBfX19fLi0nPT09PT09CiAgICAgICAgICAgICAgICAgICBgPS0tLT0nCl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXgogICAgICAgICAgICAgICAgIOS9m+elluS/neS9kSAgICAgICDmsLjml6BCVUc=";
		byte[] decode = null;
		try {
			decode = Base64Util.decodeBase64(fozuStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("\n" + new String(decode, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fozuStr;
	}
    
}
