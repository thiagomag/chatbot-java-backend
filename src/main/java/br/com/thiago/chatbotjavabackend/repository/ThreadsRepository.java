package br.com.thiago.chatbotjavabackend.repository;

import br.com.thiago.chatbotjavabackend.entity.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThreadsRepository extends JpaRepository<Threads, Long> {

    Optional<Threads> findByThreadId(String threadId);
}