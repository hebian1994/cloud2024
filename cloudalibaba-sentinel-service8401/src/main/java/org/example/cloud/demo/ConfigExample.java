package org.example.cloud.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Executor;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * Config service example
 *
 * @author Nacos
 */
public class ConfigExample {

    @SneakyThrows
    public static void main(String[] args) throws NacosException, InterruptedException {
        String serverAddr = "localhost:8848";
        String dataId = "cloud-alibaba-sentinel-service-rule-flow";
        String group = "DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        //properties.put(PropertyKeyConst.NAMESPACE, "public");
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);


        ObjectMapper objectMapper = new ObjectMapper();

        List sourceArray = objectMapper.readValue("[{\n" +
                        "    \"resource\":\"/rateLimit/byUrl\",\n" +
                        "    \"limitApp\":\"default\",\n" +
                        "    \"grade\":1,\n" +
                        "    \"count\":2,\n" +
                        "    \"strategy\":0,\n" +
                        "    \"controlBehavior\":0,\n" +
                        "    \"clusterMode\":false\n" +
                        "}]",
                new TypeReference<List<HashMap>>() {
                });

        sourceArray.forEach(System.out::println);

    }
}
