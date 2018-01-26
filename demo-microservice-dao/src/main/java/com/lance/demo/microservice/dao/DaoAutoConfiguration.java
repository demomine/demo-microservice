package com.lance.demo.microservice.dao;

//import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.spring.MybatisMapperRefresh;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.baomidou.mybatisplus.spring.boot.starter.MybatisPlusAutoConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
@EnableConfigurationProperties(DaoAutoConfigProperties.class)
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
@ConditionalOnClass({SqlSessionFactory.class, MybatisSqlSessionFactoryBean.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DaoAutoConfiguration {
    @Autowired
    private DaoAutoConfigProperties daoAutoConfigProperties;

    @Bean
    @ConditionalOnProperty(value = "framework.dao.explain.enabled",havingValue = "true")
    public SqlExplainInterceptor sqlExplainInterceptor() {
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        sqlExplainInterceptor.setStopProceed(daoAutoConfigProperties.getExplain().isStopProceed());
        return sqlExplainInterceptor;
    }

    @Bean
    @ConditionalOnProperty(value = "framework.dao.performance.enabled" ,havingValue = "true")
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setFormat(daoAutoConfigProperties.getPerformance().isFormat());
        performanceInterceptor.setMaxTime(daoAutoConfigProperties.getPerformance().getMaxTime());
        return performanceInterceptor;
    }

    @Bean
    @ConditionalOnProperty(value = "framework.dao.locker.enabled" ,havingValue = "true")
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    @ConditionalOnProperty(value = "framework.dao.pagination.enabled" ,havingValue = "true")
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    //@ConditionalOnProperty(name = "framework.dao.refresh.enabled")
    public MybatisMapperRefresh mybatisMapperRefresh(SqlSessionFactory sqlSessionFactory){
        return new MybatisMapperRefresh(daoAutoConfigProperties.getRefresh().getMapperLocation(),
                sqlSessionFactory, daoAutoConfigProperties.getRefresh().getDelay(),
                daoAutoConfigProperties.getRefresh().getSleep(), daoAutoConfigProperties.getRefresh().isEnabled());
    }

//    @Bean
//    @ConditionalOnProperty(value = "framework.dao.monitor.enabled" ,havingValue = "true")
//    public DruidStatInterceptor druidStatInterceptor() {
//        return new DruidStatInterceptor();
//    }
}
