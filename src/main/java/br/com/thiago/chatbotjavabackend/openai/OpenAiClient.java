package br.com.thiago.chatbotjavabackend.openai;


import br.com.thiago.chatbotjavabackend.controller.dto.ChatBotRequest;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.messages.MessageRequest;
import com.theokanning.openai.runs.CreateThreadAndRunRequest;
import com.theokanning.openai.runs.Run;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.threads.ThreadRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

@Component
public class OpenAiClient {

    private final OpenAiService service;

    @Value("${chatbot.openai.assistant.id}")
    String assistantId;

    @Value("${chatbot.openai.api.key}")
    String apiKey;

    public OpenAiClient() {
        this.service = new OpenAiService(apiKey, Duration.ofSeconds(60));
    }

    public String sendMessage(ChatBotRequest dados) {
        if (dados.getThreadId() == null) {
            final var runRequest = buildThreadAndRunRequest(dados);

            final var run = service.createThreadAndRun(runRequest);
            dados.setThreadId(run.getThreadId());

            runRetrieve(dados, run);
        }

        final var messageResponse = service.listMessages(dados.getThreadId())
                .getData()
                .stream()
                .max(Comparator.comparingInt(Message::getCreatedAt));

        return messageResponse.map(message -> message.getContent().get(0).getText().getValue()
                .replaceAll("【.*?】", "")).orElse("Não foi possível obter resposta do assistente");
    }

    private CreateThreadAndRunRequest buildThreadAndRunRequest(ChatBotRequest dados) {
        return CreateThreadAndRunRequest.builder()
                .assistantId(assistantId)
                .thread(ThreadRequest.builder()
                        .messages(List.of(
                                MessageRequest.builder()
                                        .role(ChatMessageRole.USER.value())
                                        .content(dados.getPromptUsuario())
                                        .build()
                        ))
                        .build())
                .build();
    }

    private void runRetrieve(ChatBotRequest dados, Run run) {
        var concluido = false;
        var precisaChamarFuncao = false;
        try {
            while (!concluido && !precisaChamarFuncao) {
                Thread.sleep(1000 * 10);
                final var runRetrieved = service.retrieveRun(dados.getThreadId(), run.getId());
                concluido = runRetrieved.getStatus().equalsIgnoreCase("completed");
                precisaChamarFuncao = run.getRequiredAction() != null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
