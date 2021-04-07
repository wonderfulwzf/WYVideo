package pxxy.wzf.system.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public HttpClientBuilder httpClientBuilder(){
        return HttpClientBuilder.create();
    }

    @Bean
    public HttpClient httpClient(){
        return httpClientBuilder().build();
    }
}
