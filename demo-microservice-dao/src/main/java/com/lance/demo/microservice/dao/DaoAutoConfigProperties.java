package com.lance.demo.microservice.dao;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "framework.dao")
@Data
public class DaoAutoConfigProperties {
    private String enumsLocation;
    private Pagination pagination = new Pagination();
    private SqlExplain explain = new SqlExplain();
    private Performance performance = new Performance();
    private OptimisticLocker locker = new OptimisticLocker();
    private MapperRefresh refresh = new MapperRefresh();
    private Sequence sequence = new Sequence();

    @Data
    static class Pagination {
        private boolean enabled = true;
    }
    @Data
    static class SqlExplain{
        private boolean enabled = false;
        private boolean stopProceed = false;
    }
    @Data
    static class Performance{
        private boolean enabled = false;
        private long maxTime = 1000;
        private boolean format = true;
    }
    @Data
    static class OptimisticLocker{
        private boolean enabled = false;
    }
    @Data
    static class MapperRefresh{
        private boolean enabled = false;
        private Resource[] mapperLocation={new ClassPathResource("classpath:mapper/*/*.xml")};
        private int delay = 10;
        private int sleep = 20;
    }
    @Data
    static class Sequence{
        private String keyGeneratorType = "1";
    }

}
