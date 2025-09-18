package com.titan.tune.Dto.Response;

import java.util.UUID;

public  record SongResponse(
        UUID trackingId,
        String titre,
        String audio,
        String artiste
) {
}
