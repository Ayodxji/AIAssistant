package com.example.AIAssistant.email;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class EmailAssistantService {

    private final RestClient restClient;

    public EmailAssistantService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String generateEmailResponse(Email email){
        String prompt = buildPrompt(email);
        Map<String,Object> requestBody = Map.of("contents", List.of(
                Map.of("parts",List.of(
                        Map.of("text",prompt)
                ))
        ));
        String response = restClient.post()
                .uri("")
                .header("Content-Type","application/json")
                .body(requestBody)
                .retrieve()
                .body(String.class);
        return extractResponseContent(response);
    }

    private String buildPrompt(Email email){
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional reply for the following email content. Please don't generate a subject line");
        if (email.getEmailTone() != null && !email.getEmailTone().isEmpty()){
            prompt.append("Use a ").append(email.getEmailTone()).append(" tone. ");
        }
        prompt.append("\nOriginal email: \n").append(email.getEmailContent());
        return prompt.toString();

    }
    private String extractResponseContent(String response){
        try{
            ObjectMapper mapper  = new ObjectMapper();
            JsonNode JsonNode = mapper.readTree(response);
           return JsonNode.path("candidates")
                   .get(0)
                   .path("content")
                   .path("parts")
                   .get(0)
                   .path("text")
                   .asText();
        }catch (Exception e){
            return "Error Processing Request "+e.getMessage();
        }
    }
}
