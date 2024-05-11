package br.com.thiago.chatbotjavabackend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@Table("user_threads")
public class UserThreads {

    @Id
    private Long id;
    private Long userId;
    private Long threadId;
}
