package com.ovit.nswy.im.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.ovit.nswy.im.properties.DruidConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * DESCRIPTION:
 * DruidConfig
 * @author zouyan
 * @create 2017-05-26 13:56
 * Created by fuck~ on 2017/5/26.
 */
@Configuration
public class DruidConfig {

    private static final Logger logger = LogManager.getLogger(DruidConfig.class);

    @Bean
    public DataSource druidDataSource(DruidConfigProperties druidConfigProperties) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(druidConfigProperties.getDriverClassName());
        druidDataSource.setUrl(druidConfigProperties.getUrl());
        druidDataSource.setUsername(druidConfigProperties.getUsername());
        druidDataSource.setPassword(druidConfigProperties.getPassword());
        druidDataSource.setInitialSize(druidConfigProperties.getInitialSize());
        druidDataSource.setMinIdle(druidConfigProperties.getMinIdle());
        druidDataSource.setMaxActive(druidConfigProperties.getMaxActive());
        druidDataSource.setMaxWait(druidConfigProperties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(druidConfigProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(druidConfigProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(druidConfigProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(druidConfigProperties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(druidConfigProperties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(druidConfigProperties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(druidConfigProperties.isPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidConfigProperties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setConnectionProperties(druidConfigProperties.getConnectionProperties());
//        druidDataSource.setUseGlobalDataSourceStat();
        try {
            druidDataSource.setFilters(druidConfigProperties.getFilters());
            druidDataSource.init();
        } catch (SQLException e) {
            logger.error("druidDataSource init error",e);
        }

        return druidDataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean druidServlet(DruidConfigProperties druidConfigProperties) {

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //添加初始化参数：initParams

        //白名单：
        //servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        //servletRegistrationBean.addInitParameter("deny","192.168.1.73");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", druidConfigProperties.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", druidConfigProperties.getLoginPassword());
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        return servletRegistrationBean;

    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
