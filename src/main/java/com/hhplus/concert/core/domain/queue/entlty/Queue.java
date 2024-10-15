package com.hhplus.concert.core.domain.queue.entlty;

import com.hhplus.concert.core.domain.user.entlty.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "QUEUE")
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(name = "token", nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private QueueStatus status;

    @Column(name = "entered_dt", nullable = false)
    private LocalDateTime enteredDt;

    @Column(name = "expired_dt")
    private LocalDateTime expiredDt;

    public Queue(Long userId, String token) {
        this.users = new Users(userId);
        this.token = token;
        this.status = QueueStatus.WAITING;
        this.enteredDt = LocalDateTime.now();
        this.expiredDt = null;
    }

    public Queue(Long userId, String token, QueueStatus status, LocalDateTime expiredDt) {
        this.users = new Users(userId);
        this.token = token;
        this.status = status;
        this.enteredDt = LocalDateTime.now();
        this.expiredDt = expiredDt;
    }

    // 토큰 유효성 검증 로직
    public boolean isTokenValid() {
        // 토큰 상태가 WAITING 또는 PROGRESS일 때 유효
        if (status == QueueStatus.WAITING || status == QueueStatus.PROGRESS) {
            // 만료 시간이 없거나, 만료 시간이 현재 시간 이후일 때 유효
            return expiredDt == null || expiredDt.isAfter(LocalDateTime.now());
        }

        return false; // 그 외의 경우 토큰은 유효하지 않음
    }

    // 새로운 토큰을 발급하거나 기존 토큰을 반환하는 로직
    public static Queue enterQueue(Queue existingQueue, Long userId) {
        if (existingQueue != null && existingQueue.isTokenValid()) {
            return existingQueue; // 기존 유효 토큰 반환
        }
        return new Queue(userId, generateNewToken()); // 새 토큰 발급
    }

    // 새로운 토큰 생성
    private static String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    // 큐 체크 후 출입 여부 return
    public static Queue checkWatingQueue(List<Queue> queueList, Queue queue) {
        if(queueList.size() < 30) {
            // 10분 증가
            LocalDateTime expiredDt = queue.getExpiredDt().plusMinutes(10);

            // 큐 진입 가능
            return new Queue(queue.getUsers().getId(), queue.getToken(), QueueStatus.PROGRESS, expiredDt);
        } else {
            return queue;
        }
    }
}
