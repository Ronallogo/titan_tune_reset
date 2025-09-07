package com.titan.tune.Dto.Request;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public  record SongRequest(
        String titre,
        MultipartFile audio,
        UUID albumTrackingId,
        UUID categorieTrackingId
) {
}
