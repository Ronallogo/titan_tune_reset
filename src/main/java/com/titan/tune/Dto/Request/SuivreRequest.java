package com.titan.tune.Dto.Request;

import java.util.UUID;

public record SuivreRequest(
        UUID trackingIdFollower,
        UUID trackingIdFollowing
) {
}
