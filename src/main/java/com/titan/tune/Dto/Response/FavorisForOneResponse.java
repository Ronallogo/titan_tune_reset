package com.titan.tune.Dto.Response;

import java.time.LocalDateTime;
import java.util.UUID;

public record FavorisForOneResponse(
        UUID trackingId,
        String songTitre,
        String songArtiste,
        LocalDateTime dateAjout
) {
}
