package br.com.thiago.chatbotjavabackend.repository;

import br.com.thiago.chatbotjavabackend.entity.OpenaAiThreads;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThreadsRepository extends JpaRepository<OpenaAiThreads, Long> {

    Optional<OpenaAiThreads> findByThreadId(String threadId);
}