package pl.edu.amu.dji.jms.lab11.books;

import com.google.common.cache.CacheBuilder;
import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;
import java.util.concurrent.TimeUnit;

@ComponentScan
@EnableAutoConfiguration
@RestController
@EnableCaching
public class BookConfiguration {

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public Filter shallowETagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Bean
    public CacheManager cacheManager(){
        CacheBuilder cacheBuilder =  CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.SECONDS);
        GuavaCacheManager cacheManager = new GuavaCacheManager("reviews");
        cacheManager.setCacheBuilder(cacheBuilder);

        return cacheManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookConfiguration.class, args);
    }
}
