package br.com.thiago.chatbotjavabackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@Table(name = "assistants")
@Entity
public class OpenAiAssistant {

    @Id
    private String assistantId;
    private Integer createdAt;
    private String name;
    private String description;
    @NonNull
    private String model;
}
