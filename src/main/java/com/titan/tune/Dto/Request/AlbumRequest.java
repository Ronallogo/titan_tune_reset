package com.titan.tune.Dto.Request;

import java.util.UUID;

public  record AlbumRequest(
        String titreAlbum,
        UUID categorieTrackingId,
        UUID artisteTrackingId
){
}
