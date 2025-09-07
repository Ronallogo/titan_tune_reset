package com.titan.tune.Dto.Response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ArtistResponse(
        UUID trackingId,
        String  FirstName ,
        String LastName ,
        String Alias,
        String Phone ,
        String Email ,
        String Password ,
        String description

) {
}
