package com.hhplus.concert.app.interfaces.v1.concert.req;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReserveConcertReq(
        @Schema(description = "콘서트 스케쥴 id", defaultValue = "1")
        long scheduleId,
        @Schema(description = "콘서트 좌석 id", defaultValue = "1")
        long seatId
) {}
