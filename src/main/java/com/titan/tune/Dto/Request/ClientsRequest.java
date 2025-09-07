package com.titan.tune.Dto.Request;


public record ClientsRequest(

        String firstName,

        String lastName,

        String email,

        String password ,

        String phone
) {
}
