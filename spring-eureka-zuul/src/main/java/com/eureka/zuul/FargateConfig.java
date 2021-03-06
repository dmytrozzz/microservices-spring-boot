package com.eureka.zuul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.AmazonInfo;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class FargateConfig {
    /**
     * We run service in fargate so override default IP when in fargate profile
     */
    @SneakyThrows
    @Bean
    @Profile("fargate")
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils,
                                                         @Value("${server.port}") Integer port,
                                                         @Value("${ECS_CONTAINER_METADATA_URI_V4}") String metaUrl) {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(URI.create(metaUrl))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info(response.body());
        var privateIp = new ObjectMapper()
                .readTree(response.body())
                .get("Networks").get(0).get("IPv4Addresses").get(0).asText();

        config.setHostname(privateIp);
        config.setIpAddress(privateIp);
        config.setNonSecurePort(port);

        return config;
    }
}
