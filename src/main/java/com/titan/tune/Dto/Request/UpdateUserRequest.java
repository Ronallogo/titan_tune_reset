package com.titan.tune.Dto.Request;

public record UpdateUserRequest(
        String nom,
        String email,
        String surnom,
        String prenom
) {
}
