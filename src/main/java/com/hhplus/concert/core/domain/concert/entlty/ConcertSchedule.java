package com.hhplus.concert.core.domain.concert.entlty;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "CONCERT_SCHEDULE")
public class ConcertSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Column(name = "open_dt", nullable = false)
    private LocalDate openDt;

    @Column(name = "start_dt", nullable = false)
    private LocalDateTime startDt;

    @Column(name = "end_dt", nullable = false)
    private LocalDateTime endDt;

    @Column(name = "total_seat", nullable = false)
    private Integer totalSeat;

    @Column(name = "reservation_seat", nullable = false)
    private Integer reservationSeat;

    @Enumerated(EnumType.STRING)
    @Column(name = "total_seat_status", nullable = false)
    private SeatStatus totalSeatStatus;

    @Column(name = "created_dt", nullable = false)
    private LocalDateTime createdDt;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;

    enum SeatStatus {
        SOLD_OUT,
        AVAILABLE
    }
}