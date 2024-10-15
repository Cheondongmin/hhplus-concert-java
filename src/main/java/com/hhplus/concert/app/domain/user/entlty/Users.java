package com.hhplus.concert.app.domain.user.entlty;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_mail", nullable = false)
    private String userMail;

    @Column(name = "user_amount", nullable = false)
    private Long userAmount;

    @Column(name = "created_dt", nullable = false)
    private LocalDateTime createdDt;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;

    public Users(Long userId) {
        String randomEmail = generateRandomEmail(); // 이메일 자동 생성
        this.id = userId;
        this.userMail = randomEmail;
        this.userAmount = 0L;
        this.createdDt = LocalDateTime.now();
        this.isDelete = false;
    }

    // 랜덤 이메일 생성 메서드
    private static String generateRandomEmail() {
        String uuid = UUID.randomUUID().toString();
        return uuid + "@gmail.com"; // 랜덤 UUID 기반 이메일 생성
    }
}