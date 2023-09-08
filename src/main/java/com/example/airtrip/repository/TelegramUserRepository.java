package com.example.airtrip.repository;

import com.example.airtrip.domain.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
}
