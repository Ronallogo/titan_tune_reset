package com.titan.tune.Service;


import com.titan.tune.Dto.Response.SuivreResponse;

import java.util.List;
import java.util.UUID;

public interface SuivreService {

    SuivreResponse follow(UUID trackingIdClientParam , UUID trackingIdArtisteParam );

    Integer nbrFollowers(UUID trackingIdArtiste);
    Integer nbrFollowing(UUID trackingIdArtiste);

    void unfollow(UUID trackingIdFollowers, UUID trackingIdFollowing);

    List<SuivreResponse> followers(UUID trackingIdArtiste);

    List<SuivreResponse> following(UUID trackingIdClient);

    boolean checkIfRelationExist(UUID trackingIdFollower , UUID trackingIdFollowing);
}
