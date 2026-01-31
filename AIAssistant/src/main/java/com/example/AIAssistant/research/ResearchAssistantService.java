package com.example.AIAssistant.research;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ResearchAssistantService {
    private RestClient restClient;
    public String processContent(Research research){
        String prompt = buildPrompt(research);
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

    private String buildPrompt(Research research){
        StringBuilder prompt = new StringBuilder();
        switch (research.getOperation()){
            case "summarize":
                prompt.append("Provide a clear summary of the following text.");
                break;
            case "suggest":
                prompt.append("Based on the following content: suggest related topics and further reading. Format the response with clear headings and bullet points.");
                break;
            default:
                throw new IllegalArgumentException("Unknown Operation: "+research.getOperation());
        }
        prompt.append(research.getContent());
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
            return "Error Parsing: "+e.getMessage();
        }
    }
}
