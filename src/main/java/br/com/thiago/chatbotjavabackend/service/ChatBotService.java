package br.com.thiago.chatbotjavabackend.service;

import br.com.thiago.chatbotjavabackend.controller.dto.ChatBotRequest;
import br.com.thiago.chatbotjavabackend.openai.OpenAiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatBotService {

    private final OpenAiClient openAiClient;

    public String execute(ChatBotRequest chatBotRequest) {
        return openAiClient.sendMessage(chatBotRequest);
    }
}
