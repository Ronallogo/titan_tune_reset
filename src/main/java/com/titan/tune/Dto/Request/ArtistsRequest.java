package com.titan.tune.Dto.Request;

import lombok.Builder;

@Builder
public record ArtistsRequest(
        String  firstName ,
        String lastName ,
        String alias,
        String phone ,
        String email ,
        String password ,
        String description
) {
}
