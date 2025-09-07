package com.titan.tune.Dto.Response;



import com.titan.tune.Entity.User;

import java.time.Instant;
import java.util.UUID;

public record ValidationResponse(
        UUID trackingId,
        Instant creation,
        Instant expiration,
        Instant activation,
        String code,
        User user
) {
}
