package com.titan.tune.Dto.Response;


import java.util.UUID;

public record SongEcouterResponse(
        UUID trackingId ,
        String emailUser ,
        String   titreSong
) {
}