package com.hhplus.concert.core.domain.queue;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckQueueUnitTest {
    // 유저 대기열 토큰 체크  API 유닛 테스트 코드

    @Test
    void 대기열이_30명_미만일_경우_상태가_PROGRESS로_변경된다() {
        // given
        Queue queue = new Queue(1L, "test-token", QueueStatus.WAITING, LocalDateTime.now().plusMinutes(10));
        List<Queue> queueList = Collections.nCopies(30, queue); // 30명의 대기열을 생성

        // when
        queue.checkWatingQueue(queueList); // 상태가 내부에서 변경됨

        // then
        assertEquals(QueueStatus.PROGRESS, queue.getStatus()); // 상태가 PROGRESS로 변경되어야 함
    }

    @Test
    void 대기열이_30명_초과면_상태가_변경되지_않는다() {
        // given
        Queue queue = new Queue(1L, "test-token", QueueStatus.WAITING, LocalDateTime.now().plusMinutes(10));
        List<Queue> queueList = Collections.nCopies(31, queue); // 31명의 대기열을 생성

        // when
        queue.checkWatingQueue(queueList); // 상태가 내부에서 변경됨

        // then
        assertEquals(QueueStatus.WAITING, queue.getStatus()); // 상태가 그대로 WAITING이어야 함
    }
}
