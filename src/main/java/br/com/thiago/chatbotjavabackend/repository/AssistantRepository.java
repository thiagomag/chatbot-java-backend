package br.com.thiago.chatbotjavabackend.repository;

import br.com.thiago.chatbotjavabackend.entity.OpenAiAssistant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantRepository extends JpaRepository<OpenAiAssistant, Long> {
}
