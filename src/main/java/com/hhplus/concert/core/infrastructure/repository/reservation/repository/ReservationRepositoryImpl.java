package com.hhplus.concert.core.infrastructure.repository.reservation.repository;

import com.hhplus.concert.core.domain.reservation.Reservation;
import com.hhplus.concert.core.domain.reservation.ReservationRepository;
import com.hhplus.concert.core.infrastructure.repository.reservation.persistence.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJpaRepository jpaRepository;

    @Override
    public void save(Reservation reservation) {
        jpaRepository.save(reservation);
    }

    @Override
    public Reservation findById(long reservationId) {
        return jpaRepository.findById(reservationId).orElseThrow(
                () -> new NullPointerException("해당 예약 정보가 존재하지 않습니다.")
        );
    }

    @Override
    public List<Reservation> findAll() {
        return jpaRepository.findAll();
    }
}
