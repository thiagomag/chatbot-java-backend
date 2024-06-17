package br.com.thiago.chatbotjavabackend.openai;


import br.com.thiago.chatbotjavabackend.controller.dto.ChatBotRequest;
import com.theokanning.openai.assistants.Assistant;
import com.theokanning.openai.assistants.AssistantRequest;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.messages.MessageRequest;
import com.theokanning.openai.runs.CreateThreadAndRunRequest;
import com.theokanning.openai.runs.Run;
import com.theokanning.openai.runs.RunCreateRequest;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.threads.ThreadRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class OpenAiClient {

    private final OpenAiService service;

    private final String assistantId;

    public OpenAiClient(@Value("${chatbot.openai.assistant.id}") String assistantId,
                        @Value("${chatbot.openai.api.key}") String apiKey) {
        this.service = new OpenAiService(apiKey, Duration.ofSeconds(60));
        this.assistantId = assistantId;
    }

    public Optional<Message> sendMessage(ChatBotRequest dados) {

        Run run;
        if (dados.getThreadId() == null) {
            final var runRequest = buildThreadAndRunRequest(dados);
            run = service.createThreadAndRun(runRequest);
            dados.setThreadId(run.getThreadId());
        } else {
            service.createMessage(dados.getThreadId(), buildMessage(dados));
            run = service.createRun(dados.getThreadId(), buildRunRequest());
        }

        runRetrieve(dados, run);

        return service.listMessages(dados.getThreadId())
                .getData()
                .stream()
                .max(Comparator.comparingInt(Message::getCreatedAt));
    }

    private CreateThreadAndRunRequest buildThreadAndRunRequest(ChatBotRequest dados) {
        return CreateThreadAndRunRequest.builder()
                .assistantId(assistantId)
                .thread(ThreadRequest.builder()
                        .messages(List.of(buildMessage(dados)))
                        .build())
                .build();
    }

    private MessageRequest buildMessage(ChatBotRequest dados) {
        return MessageRequest.builder()
                .role(ChatMessageRole.USER.value())
                .content(dados.getPromptUsuario())
                .build();
    }

    private RunCreateRequest buildRunRequest() {
        return RunCreateRequest
                .builder()
                .assistantId(assistantId)
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

    public Assistant createAssistant(AssistantRequest assistantRequest) {
        return service.createAssistant(assistantRequest);
    }
}
