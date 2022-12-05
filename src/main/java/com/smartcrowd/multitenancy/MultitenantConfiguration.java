package com.smartcrowd.multitenancy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

@Configuration
public class MultitenantConfiguration {

  @Bean
  @ConfigurationProperties(prefix = "tenants")
  public DataSource dataSource() {
    File[] files = Paths.get("allFiles").toFile().listFiles();
    Map<Object, Object> resolvedDataSources = new HashMap<>();

    for (File propertyFile : files) {
      Properties tenantProperties = new Properties();
      DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

      try {
        tenantProperties.load(new FileInputStream(propertyFile));
        String tenantId = tenantProperties.getProperty("name");
        System.out.println("Name:"+tenantId);
        dataSourceBuilder.driverClassName(tenantProperties.getProperty("datasource.driver-class-name"));
        dataSourceBuilder.username(tenantProperties.getProperty("datasource.username"));
        dataSourceBuilder.password(tenantProperties.getProperty("datasource.password"));
        dataSourceBuilder.url(tenantProperties.getProperty("datasource.url"));
        System.out.println("Datasource:"+tenantProperties.getProperty("datasource.driver-class-name")+tenantProperties.getProperty("datasource.username")+tenantProperties.getProperty("datasource.password")+tenantProperties.getProperty("datasource.url"));
        resolvedDataSources.put(tenantId, dataSourceBuilder.build());
      } catch (IOException exp) {
        throw new RuntimeException("Problem in tenant datasource:" + exp);
      }
    }

    AbstractRoutingDataSource dataSource = new MultitenantDataSource();
    System.out.println("Coming here");
    dataSource.setDefaultTargetDataSource(resolvedDataSources.get("dxb"));
    dataSource.setTargetDataSources(resolvedDataSources);

    dataSource.afterPropertiesSet();
    return dataSource;
  }

}