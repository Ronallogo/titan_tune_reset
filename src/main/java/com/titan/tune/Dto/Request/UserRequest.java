package com.titan.tune.Dto.Request;


public record UserRequest (
        String  FirstName ,
        String LastName ,
        String Alias,
        String Phone ,
        String Email ,
        String Password ,
        String description
){ }

