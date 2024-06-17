package br.com.thiago.chatbotjavabackend.service;

import br.com.thiago.chatbotjavabackend.controller.dto.ChatBotRequest;
import br.com.thiago.chatbotjavabackend.entity.OpenaAiThreads;
import br.com.thiago.chatbotjavabackend.entity.Users;
import br.com.thiago.chatbotjavabackend.openai.OpenAiClient;
import br.com.thiago.chatbotjavabackend.repository.AssistantRepository;
import br.com.thiago.chatbotjavabackend.repository.ThreadsRepository;
import br.com.thiago.chatbotjavabackend.repository.UserRepository;
import com.theokanning.openai.messages.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class ChatBotServiceTest {

    @Mock
    private OpenAiClient openAiClient;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ThreadsRepository threadsRepository;
    @Mock
    private AssistantRepository assistantRepository;

    private ChatBotService chatBotService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        chatBotService = new ChatBotService(openAiClient, userRepository, threadsRepository, assistantRepository);
    }

    @Test
    public void executeShouldReturnSuccessfullyApproach1() {
        //given
        final var chatBotRequest = ChatBotRequest.builder()
                .userId(1L)
                .promptSistema("promptSistema")
                .promptUsuario("promptUsuario")
                .threadId("threadId")
                .build();
        when(openAiClient.sendMessage(chatBotRequest))
                .thenReturn(Optional.of(Message.builder().build()));
        when(threadsRepository.findByThreadId(chatBotRequest.getThreadId()))
                .thenReturn(Optional.of(OpenaAiThreads.builder().threadId("1").assistantId("1").build()));
        when(userRepository.findById(chatBotRequest.getUserId()))
                .thenReturn(Optional.of(Users.builder().id(1L).name("teste").build()));
        when(userRepository.save(Users.builder().id(1L).name("teste").build()))
                .thenReturn(Users.builder().id(1L).name("teste").build());

        //when
        //then
    }
}
