package com.titan.tune.Service;

import com.titan.tune.Dto.Response.SongEcouterForOneUser;
import com.titan.tune.Dto.Response.SongEcouterResponse;
import com.titan.tune.Dto.Response.SongResponse;

import java.util.List;
import java.util.UUID;

public interface SongEcouterService {

    SongEcouterResponse create(UUID trackingIdUser , UUID trackingIdSong) ;

    void delete(UUID trackingIdSongEcouter );

    List<SongEcouterForOneUser> searchForOneBytrackingId(UUID trackingIdUser);
    List<SongEcouterResponse>searchByTitle(String titreSong);

    SongEcouterResponse get(UUID trackingIdSongEcouter) ;

    List<SongEcouterResponse> getAll() ;


    List<SongEcouterForOneUser> getAllForOne(UUID trackingIdUser) ;


}