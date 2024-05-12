package br.com.thiago.chatbotjavabackend.repository;

import br.com.thiago.chatbotjavabackend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
