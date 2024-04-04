package org.example.cloud.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义路由规则 请求优先下发到指定的metadata.server-name
 * 主体方法拷贝至RoundRobinLoadBalancer
 * https://blog.csdn.net/qq_40224163/article/details/128658018
 *
 * @see org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer#choose(Request)
 */
public class ServerNameLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private static final Log log = LogFactory.getLog(ServerNameLoadBalancer.class);

    final String serviceId;

    final AtomicInteger position;

    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public static String INSTANCE_SERVER_NAME = "server-name";

    /**
     * @param serviceInstanceListSupplierProvider a provider of
     *                                            {@link ServiceInstanceListSupplier} that will be used to get available instances
     * @param serviceId                           id of the service for which to choose an instance
     */
    public ServerNameLoadBalancer(String serviceName, ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
        INSTANCE_SERVER_NAME = serviceName;
    }

    public ServerNameLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId, int seedPosition) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.position = new AtomicInteger(seedPosition);
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {

        log.info("loadbalancer choose {}" + request);
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        // 这里额外传入request对象用于解析请求头
        return supplier.get(request).next()
                .map(serviceInstances -> this.processInstanceResponse(request, supplier, serviceInstances));
    }

    private Response<ServiceInstance> processInstanceResponse(Request request, ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances) {
        // request对象带入getInstanceResponse方法
        Response<ServiceInstance> serviceInstanceResponse = this.getInstanceResponse(request, serviceInstances);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(Request request, List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }
        // 获取请求头server-name属性值
        String serverName = StringUtils.EMPTY;
        // 路由实例
        ServiceInstance instance = null;
        if (null != request.getContext() && request.getContext() instanceof RequestDataContext) {
            List<String> serverNames = ((RequestDataContext) request.getContext()).getClientRequest().getHeaders().get(INSTANCE_SERVER_NAME);
            // 有且只取其中一个
            serverName = Optional.ofNullable(serverNames).map(m -> m.get(0)).orElse(StringUtils.EMPTY);
        }
        // Metadata.server-name 优先匹配
        for (int i = 0; i < instances.size(); i++) {
            ServiceInstance serviceInstance = instances.get(i);
            // serverName一致
            if (StringUtils.equals(serviceInstance.getMetadata().get(INSTANCE_SERVER_NAME), serverName)) {
                instance = serviceInstance;
                break;
            }
        }
        // instance为空说明未配置server-name,或请求头未带server-name，走默认路由规则
        if (null == instance) {
            // Ignore the sign bit, this allows pos to loop sequentially from 0 to
            // Integer.MAX_VALUE
            int pos = this.position.incrementAndGet() & Integer.MAX_VALUE;

            instance = instances.get(pos % instances.size());
        }
        return new DefaultResponse(instance);
    }
}
