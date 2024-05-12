package br.com.thiago.chatbotjavabackend.repository;

import br.com.thiago.chatbotjavabackend.entity.Threads;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThreadsRepository extends JpaRepository<Threads, Long> {

    Optional<Threads> findByThreadId(String threadId);
}