package com.example.AIAssistant.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(
            RestClient.Builder builder,
            @Value("${api.gemini.url}") String apiUrl,
            @Value("${api.gemini.key}") String apiKey
    ) {
        return builder
                .baseUrl(apiUrl + "?key=" + apiKey)
                .build();
    }
}