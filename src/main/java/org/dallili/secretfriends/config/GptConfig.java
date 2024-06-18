package org.dallili.secretfriends.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GptConfig {
    @Value("${openai.api.key}")
    private String apiKey;
    @Value("${openai.project.id}")
    private String projectId;
    @Value("${openai.organization.id}")
    private String organizationId;

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution)->{
            request.getHeaders().add(
                    "Authorization"
                    ,"Bearer "+apiKey);
            request.getHeaders().add(
                    "OpenAI-Organization"
                    , organizationId);
            request.getHeaders().add(
                    "OpenAI-Project"
                    , projectId);
            return execution.execute(request,body);
        });
        return restTemplate;
    }
}
