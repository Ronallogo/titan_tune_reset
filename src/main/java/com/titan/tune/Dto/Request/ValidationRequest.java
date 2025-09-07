package com.titan.tune.Dto.Request;



import com.titan.tune.Entity.User;

import java.time.Instant;

public record ValidationRequest(
        Instant creation,
        Instant expiration,
        Instant activation,
        String code,
        User user

)
{ }
