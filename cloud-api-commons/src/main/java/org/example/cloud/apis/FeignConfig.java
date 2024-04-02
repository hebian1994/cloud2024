package org.example.cloud.apis;


import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {


    //@Bean
    //public Request.Options feignOptions() {
    //    return new Request.Options(5000, 5000); // 连接超时和读取超时都是5000毫秒
    //}

    @Bean
    public Retryer retry() {
        return new Retryer.Default(100, 1, 3);
    }

    // 日志记录级别
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
