package com.hhplus.concert.app.domain.concert.entlty;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "CONCERT_SEAT")
public class ConcertSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "concert_schedule_id", nullable = false)
    private ConcertSchedule concertSchedule;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_status", nullable = false)
    private SeatStatus seatStatus;

    @Column(name = "reserved_until_dt")
    private LocalDateTime reservedUntilDt;

    @Column(name = "created_dt", nullable = false)
    private LocalDateTime createdDt;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;

    enum SeatStatus {
        AVAILABLE,
        TEMP_RESERVED,
        RESERVED
    }
}