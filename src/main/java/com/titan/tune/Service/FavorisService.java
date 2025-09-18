package com.titan.tune.Service;


import com.titan.tune.Dto.Request.FavorisRequest;
import com.titan.tune.Dto.Response.FavorisForOneResponse;
import com.titan.tune.Dto.Response.FavorisResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface FavorisService {

    FavorisResponse addFavoris(FavorisRequest request);
    List<FavorisResponse> getAllFavoris();
    FavorisResponse getFavorisByTrackingId(UUID trackingId);
    void deleteFavoris(UUID trackingId);

    List<FavorisForOneResponse>  getAllForOneUser(UUID trackingIdUser) ;
}
