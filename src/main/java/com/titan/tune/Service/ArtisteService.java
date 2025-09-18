package com.titan.tune.Service;


import com.titan.tune.Dto.Request.ArtistsRequest;
import com.titan.tune.Dto.Response.ArtistResponse;

import java.util.List;
import java.util.UUID;

public interface ArtisteService {


    ArtistResponse create(ArtistsRequest request);

    ArtistResponse update(ArtistsRequest request , UUID trackingId);

    List<ArtistResponse> getAll();

    void delete(UUID trackingId);

    ArtistResponse get(UUID trackingId);



}
