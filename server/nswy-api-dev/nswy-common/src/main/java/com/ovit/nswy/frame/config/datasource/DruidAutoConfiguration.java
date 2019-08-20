package com.ovit.nswy.frame.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by ${huipei.x} on 2017-9-1.
 */
@Configuration
   @EnableConfigurationProperties(DatasourceProperties.class)
   @ConditionalOnClass(DruidDataSource.class)
   @ConditionalOnProperty(prefix = "spring.datasource.default", name = "url")
   @AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DruidAutoConfiguration {

   @Autowired
   private DatasourceProperties datasourceProperties;

   @Bean
   public DataSource dataSource() {
      DruidDataSource dataSource = new DruidDataSource();
      dataSource.setUrl(datasourceProperties.getUrl());
      dataSource.setUsername(datasourceProperties.getUsername());
      dataSource.setPassword(datasourceProperties.getPassword());
      if (datasourceProperties.getInitialSize() > 0) {
         dataSource.setInitialSize(datasourceProperties.getInitialSize());
      }
      if (datasourceProperties.getMinIdle() > 0) {
         dataSource.setMinIdle(datasourceProperties.getMinIdle());
      }
      if (datasourceProperties.getMaxActive() > 0) {
         dataSource.setMaxActive(datasourceProperties.getMaxActive());
      }
      dataSource.setTestOnBorrow(datasourceProperties.isTestOnBorrow());
      try {
         dataSource.init();
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
      return dataSource;
   }
}
