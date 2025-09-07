package com.titan.tune.Dto.Request;

import java.util.UUID;

public record FavorisRequest(
        UUID ClientTrackingId,
        UUID SongTrackingId
) {}
