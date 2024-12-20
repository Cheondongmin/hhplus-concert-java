package com.hhplus.concert.core.domain.user;

import com.hhplus.concert.IntegrationTest;
import com.hhplus.concert.core.domain.queue.Queue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CompletableFuture;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UsersServiceTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Nested
    class ConcurrencyTests {
        @Test
        void 잔액_1만원인_유저가_1000원_2000원_3000원을_동시에_충전하면_1만6천원이_된다() throws InterruptedException {
            // given
            Users user = new Users(1L, 10000L);
            userRepository.save(user);
            String token = Queue.generateJwtToken(user.getId());

            // when: 1000원, 2000원, 3000원 동시 충전
            CompletableFuture.allOf(
                    CompletableFuture.runAsync(() -> userService.chargeUserAmount(token, 1000L)),
                    CompletableFuture.runAsync(() -> userService.chargeUserAmount(token, 2000L)),
                    CompletableFuture.runAsync(() -> userService.chargeUserAmount(token, 3000L))
            ).join();

            Thread.sleep(100L);

            // then: 10000 + 1000 + 2000 + 3000 = 16000
            long amount = userService.selectUserAmount(token);
            assertThat(amount).isEqualTo(16000L);
        }

        @Test
        void 잔액이_1만원인_유저가_1000원씩_100번_동시에_충전요청을_보낸후_잔액은_1010000원이_된다() throws InterruptedException {
            // given
            Users user = new Users(1L, 10000L);
            userRepository.save(user);
            int numberOfRequests = 1000;
            Long chargeAmount = 1000L;
            String token = Queue.generateJwtToken(user.getId());

            // when: 100번의 동시 충전 요청
            CompletableFuture<Void>[] futures = new CompletableFuture[numberOfRequests];
            for(int i = 0; i < numberOfRequests; i++) {
                futures[i] = CompletableFuture.runAsync(() -> userService.chargeUserAmount(token, chargeAmount));
            }

            CompletableFuture.allOf(futures).join();

            // then: 10000 + (1000 * 100) = 110000
            Thread.sleep(100); // 모든 트랜잭션이 완료될 때까지 잠시 대기
            Users updatedUser = userRepository.findById(1L);
            assertThat(updatedUser.getUserAmount()).isEqualTo(1010000L);
        }
    }

    @Nested
    class UserAmountTests {
        @Test
        void 잔액이_1000원인_유저가_1000원과_2000원을_충전하면_4000원이_된다() {
            // given: 초기 잔액 1000원
            userRepository.save(new Users(1L, 1000L));
            String token = Queue.generateJwtToken(1L);

            // when: 1000원, 2000원 순차 충전
            userService.chargeUserAmount(token, 1000L);
            userService.chargeUserAmount(token, 2000L);
            long userAmount = userService.selectUserAmount(token);

            // then: 1000 + 1000 + 2000 = 4000
            assertThat(userAmount).isEqualTo(4000L);
        }
    }
}