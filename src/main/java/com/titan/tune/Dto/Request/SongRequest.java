package com.titan.tune.Dto.Request;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public  record SongRequest(
        String titre,
         String audio,
        UUID albumTrackingId,
        UUID categorieTrackingId
) {
}
