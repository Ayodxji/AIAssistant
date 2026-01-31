package com.example.AIAssistant.research;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ResearchAssistantController {
    private ResearchAssistantService researchAssistantService;
    @PostMapping("/api/content/process")
    public ResponseEntity<String> processContent(@RequestBody Research research){
        String result = researchAssistantService.processContent(research);
        return ResponseEntity.ok(result);
    }
}
