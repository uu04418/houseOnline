package com.house.online.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**配置jpa的数据源**/


//指定 这是 一个配置类
@Configuration  
//开启事务
@EnableTransactionManagement 
//扫描jpa指定的包
@EnableJpaRepositories(basePackages = "com.house.online.repository") 
public class JAPConfg {
	
	
	/**创建数据源**/
	@Bean
	// 指定数据源的配置信息
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource () {
		return DataSourceBuilder.create().build();
	}
	
	/**配置hibernate适配器**/
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory () {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		//我们自己控制sql语句
		jpaVendorAdapter.setGenerateDdl(false);
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = 
				new LocalContainerEntityManagerFactoryBean();
		//设置数据集
		entityManagerFactoryBean.setDataSource(dataSource());
		//设置jps适配器
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		//配置实体类扫描的包
		entityManagerFactoryBean.setPackagesToScan("com.house.online.entity");
		return entityManagerFactoryBean;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager (EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transients = new JpaTransactionManager();
		transients.setEntityManagerFactory(entityManagerFactory);
		return transients;
		
	}

}
