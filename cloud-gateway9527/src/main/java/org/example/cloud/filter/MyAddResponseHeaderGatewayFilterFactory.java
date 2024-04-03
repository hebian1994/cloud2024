package org.example.cloud.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Objects;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * single filter to log down the request time
 */
@Component
@Slf4j
public class MyAddResponseHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<MyAddResponseHeaderGatewayFilterFactory.Config> {

    public MyAddResponseHeaderGatewayFilterFactory() {
        super(MyAddResponseHeaderGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(MyAddResponseHeaderGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            log.info("Jump into  MyAddResponseHeaderGatewayFilterFactory status is : {}", config.getStatus());
            ServerHttpRequest request = exchange.getRequest();
            String status = request.getQueryParams().getFirst("status");
            if (status == null) {
                log.error("status should not null!!!!!");
            }
            if (Objects.requireNonNull(request.getQueryParams().getFirst("status")).equalsIgnoreCase(config.getStatus())) {
                return chain.filter(exchange);
            }
            log.error("status is not right!!!!!");
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("status");
    }

    @Getter
    @Setter
    public static class Config {
        private String status;
    }

}
