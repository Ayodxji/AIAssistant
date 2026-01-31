package com.example.AIAssistant.email;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor()
@CrossOrigin(origins =  "*")
public class EmailAssistantController {
    private EmailAssistantService emailAssistantService;



    @PostMapping("/api/email/generate")
    public ResponseEntity<String> generateResponse(@RequestBody Email email){
        String response = emailAssistantService.generateEmailResponse(email);
        return ResponseEntity.ok(response);
    }

}
