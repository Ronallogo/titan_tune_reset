package com.titan.tune.Dto.Response;

import java.util.UUID;

public record ClientResponse(
        UUID trackingId,

        String firstName,

        String lastName,

        String email,

        String password ,

        String phone
) {
}