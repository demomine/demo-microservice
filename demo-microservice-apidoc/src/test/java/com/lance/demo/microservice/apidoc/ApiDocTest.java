package com.lance.demo.microservice.apidoc;

import com.lance.demo.microservice.apidoc.config.ApiDocAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
// @SpringBootTest(classes = ApiDocAutoConfiguration.class)
@RunWith(SpringRunner.class)
@WebMvcTest()
@ContextConfiguration(classes  = ApiDocAutoConfiguration.class)
public class ApiDocTest {
    static {
        System.setProperty("swagger", "true");
    }

    @Test
    public void start() throws Exception {
        TimeUnit.HOURS.sleep(1);
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject("http://127.0.0.1:8888/swagger-ui.html", String.class);
        assertThat(forObject).isNotNull();
    }
}
