package com.titan.tune.Dto.Response;


import java.util.UUID;

public record SuivreResponse(
        UUID trackingIdFollower ,
        UUID trackingIdFollowing ,
        String follower ,
        String following


) {

}
