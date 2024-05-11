package br.com.thiago.chatbotjavabackend.controller;

import br.com.thiago.chatbotjavabackend.controller.dto.ChatBotRequest;
import br.com.thiago.chatbotjavabackend.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;

    @PostMapping("/chat")
    public String sendMessage(@RequestBody ChatBotRequest chatBotRequest) {
        return chatBotService.execute(chatBotRequest);
    }
}
