package com.example.airtrip.domain.entity.entityforrestspi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"chatId"})
@Builder
public class TelegramUser {
    @Id
    private Long chatId;
    private String firstName;
    private String lastName;
    private String username;
    @CreationTimestamp
    private Timestamp registeredAt;
}
