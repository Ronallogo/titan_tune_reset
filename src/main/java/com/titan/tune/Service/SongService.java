package com.titan.tune.Service;

import com.titan.tune.Dto.Request.SongRequest;
import com.titan.tune.Dto.Response.SongResponse;

import java.util.List;
import java.util.UUID;

public interface SongService {
    SongResponse create(SongRequest request);
    SongResponse update(UUID trakingId, SongRequest request);
     void delete(UUID trakingId);
    List<SongResponse> findByNomCategorieContaining(String nomCategorie) ;

    List<SongResponse> getAll();
    List<SongResponse> getSongsByArtiste(UUID artisteTrackingId)   ;
    public SongResponse getSong(UUID trackingId) ;
}
