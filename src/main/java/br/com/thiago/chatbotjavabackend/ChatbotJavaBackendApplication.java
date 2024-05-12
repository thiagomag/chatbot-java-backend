package br.com.thiago.chatbotjavabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ChatbotJavaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotJavaBackendApplication.class, args);
    }

}
