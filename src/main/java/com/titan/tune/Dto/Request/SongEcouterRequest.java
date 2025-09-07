package com.titan.tune.Dto.Request;


import java.util.UUID;

public record SongEcouterRequest(
        UUID trackingIdUser ,
        UUID trackingIdSong

) {
}
