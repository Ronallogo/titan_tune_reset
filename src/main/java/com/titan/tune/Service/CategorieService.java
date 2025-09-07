package com.titan.tune.Service;

import com.titan.tune.Dto.Response.CategorieResponse;
import com.titan.tune.Dto.Response.SongResponse;
import com.titan.tune.Entity.Categorie;
import com.titan.tune.Entity.Song;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategorieService {
     CategorieResponse findByTrackingId(UUID trackingId);


     List<CategorieResponse> findByName(String name) ;

    void deleteByTrackingId(UUID trackingId);
}
