package com.titan.tune.Dto.Response;

import java.time.LocalDateTime;
import java.util.UUID;

public record FavorisResponse(
        UUID trackingId,
        String clientNom,
        String songTitre,
        String songArtiste,
        LocalDateTime dateAjout
) {}
