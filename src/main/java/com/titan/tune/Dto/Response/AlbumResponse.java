package com.titan.tune.Dto.Response;

import java.util.UUID;

public record AlbumResponse(
        UUID trackingId,
        String titreAlbum,
        String nomArtiste ,
        String imageAlbum
) {
}
