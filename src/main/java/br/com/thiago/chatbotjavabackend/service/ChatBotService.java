package br.com.thiago.chatbotjavabackend.service;

import br.com.thiago.chatbotjavabackend.controller.dto.ChatBotRequest;
import br.com.thiago.chatbotjavabackend.entity.Threads;
import br.com.thiago.chatbotjavabackend.openai.OpenAiClient;
import br.com.thiago.chatbotjavabackend.repository.ThreadsRepository;
import br.com.thiago.chatbotjavabackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatBotService {

    private final OpenAiClient openAiClient;
    private final UserRepository userRepository;
    private final ThreadsRepository threadsRepository;

    public String execute(ChatBotRequest chatBotRequest) {
        final var message = openAiClient.sendMessage(chatBotRequest);

        final var thread = saveThread(chatBotRequest.getThreadId());
        saveThreadToUser(thread, chatBotRequest.getUserId());

        return message.map(m -> m.getContent().get(0).getText().getValue()
                .replaceAll("【.*?】", "")).orElse("Não foi possível obter resposta do assistente");
    }

    private Threads saveThread(String threadId) {
        return threadsRepository.findByThreadId(threadId)
                .orElse(threadsRepository.save(Threads.builder()
                        .threadId(threadId)
                        .build()));
    }

    private void saveThreadToUser(Threads thread, Long userId) {
        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (user.getThreads() != null) {
            if (user.getThreads().contains(thread)) {
                log.info("Thread já associada ao usuário");
            } else {
                user.getThreads().add(thread);
            }
        } else {
            user.setThreads(List.of(thread));
        }
        userRepository.save(user);
    }

    public List<Long> getThreadsIdByUserId(Long userId) {
        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return user.getThreads().stream()
                .map(Threads::getId)
                .toList();
    }
}
