package com.titan.tune.Dto.Response;

public record UpdateUserResponse(
        String nom,
        String email,
        String surnom,
        String prenom
) {
}
