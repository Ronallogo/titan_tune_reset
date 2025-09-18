package com.titan.tune.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface StatistiqueService {


    //Nombre total d'écoutes : Le nombre de fois que chaque chanson a été écoutée.
    Map<String , Object> nbrTotalEcouteForOneSong(UUID trackingIdSong);



    Map<String , Object> nbrUserEnabled() ;

    Map<String , Object> tauxRetention(UUID trackingIdSong);



    Map<String , Object> HowManyPlaylistContainThisSong(UUID trackingIdSong);

    List<Map<String , Object>> howMayTimeEachSongWasListen();

    Map<String , Object> artistMostListen();

    Map<String  , Object> songMostListen();

    //Ajouts en favoris : Combien de fois la chanson a été "aimée" ou ajoutée aux favoris.
    Map<String , Object> nbrLikeForOneSong(UUID trackingIdSong);


    Map<String , Object> songWithMostLike() ;

    List<Map<String , Object>> DemographicDataList() ;



}
