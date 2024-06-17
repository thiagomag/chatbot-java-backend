package br.com.thiago.chatbotjavabackend.controller;

import br.com.thiago.chatbotjavabackend.controller.dto.ChatBotRequest;
import br.com.thiago.chatbotjavabackend.service.ChatBotService;
import com.theokanning.openai.assistants.AssistantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatBotController {

    private final ChatBotService chatBotService;

    @PostMapping("/assistant/message")
    public ResponseEntity<String> sendAssistantMessage(@RequestBody ChatBotRequest chatBotRequest) {
        return ResponseEntity.ok(chatBotService.execute(chatBotRequest));
    }

    @PostMapping("/assistant/create")
    public ResponseEntity<String> createAssistant(@RequestBody AssistantRequest assistantRequest) {
        return ResponseEntity.ok(chatBotService.create(assistantRequest));
    }
}
