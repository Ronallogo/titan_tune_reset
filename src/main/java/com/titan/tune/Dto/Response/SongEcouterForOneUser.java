package com.titan.tune.Dto.Response;


import java.util.UUID;

public  record SongEcouterForOneUser(
         UUID trackingIdSongEcouter ,
        String titreSong ,
        String nomArtiste ,
        String nomAlbum

) {
}
