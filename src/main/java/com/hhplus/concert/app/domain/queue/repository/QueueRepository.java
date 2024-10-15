package com.hhplus.concert.app.domain.queue.repository;

import com.hhplus.concert.app.domain.queue.entlty.Queue;

import java.util.List;
import java.util.Optional;

public interface QueueRepository {
    void save(Long userId, String token);
    Optional<Queue> findByUserId(Long userId);
    List<Queue> findAll();
}