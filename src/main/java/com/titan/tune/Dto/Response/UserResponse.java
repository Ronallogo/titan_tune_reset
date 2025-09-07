package com.titan.tune.Dto.Response;



import com.titan.tune.utils.TypeRole;

import java.util.UUID;

public record UserResponse(
        UUID trackingId ,
        String  nom ,
        String prenom ,
        String surnom ,
        String telephone ,
        String email ,
        String password ,
        TypeRole role,
        String image,
        String description
) {
}
