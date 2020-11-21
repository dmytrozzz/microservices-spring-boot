package com.eureka.gallery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.AmazonInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Configuration
@Profile("fargate")
public class FargateConfig {
    /**
     * We run service in fargate so override default IP when in fargate profile
     */
    @SneakyThrows
    @Bean
    @Profile("fargate")
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils,
                                                         @Value("${server.port}") Integer port,
                                                         @Value("${env.ECS_CONTAINER_METADATA_URI_V4}") String metaUrl) {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
        config.setDataCenterInfo(info);

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(URI.create(metaUrl))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var privateIp = new ObjectMapper()
                .readTree(response.body())
                .at("Networks").get(0).at("IPv4Addresses").get(0).asText();
        config.setIpAddress(privateIp);
        config.setNonSecurePort(port);

        return config;
    }
}
