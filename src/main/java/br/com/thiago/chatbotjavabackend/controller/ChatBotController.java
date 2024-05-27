package br.com.thiago.chatbotjavabackend.controller;

import br.com.thiago.chatbotjavabackend.controller.dto.ChatBotRequest;
import br.com.thiago.chatbotjavabackend.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatBotController {

    private final ChatBotService chatBotService;

    @PostMapping("/assistant/message")
    public String sendMessage(@RequestBody ChatBotRequest chatBotRequest) {
        return chatBotService.execute(chatBotRequest);
    }

    @GetMapping("/assistant/threads/{userId}")
    public List<Long> getThreadsIdByUserId(@PathVariable Long userId) {
        return chatBotService.getThreadsIdByUserId(userId);
    }
}
