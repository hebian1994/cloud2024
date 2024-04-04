package org.example.cloud.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * LoadBalancer配置类
 */
public class LoadBalancerConfig {

    /**
     * 参考默认实现
     *
     * @return
     * @see org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration#reactorServiceInstanceLoadBalancer
     */
    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment,
                                                                                   LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        // 加载自定义负载器
        return new ServerNameLoadBalancer("cloud-payment-service", loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }
}
