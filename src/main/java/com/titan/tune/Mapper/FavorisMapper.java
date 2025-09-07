package com.titan.tune.Mapper;


import com.titan.tune.Dto.Request.FavorisRequest;
import com.titan.tune.Dto.Response.FavorisResponse;
import com.titan.tune.Entity.Clients;
import com.titan.tune.Entity.Favoris;
import com.titan.tune.Entity.Song;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FavorisMapper {


    public Favoris toEntity(
            Clients client ,
            Song song
    ) {
        Favoris favoris = new Favoris();
        favoris.setClient(client) ;
        favoris.setSong(song);
        favoris.setTrackingId(UUID.randomUUID()) ;

        return favoris;
    }

    public FavorisResponse toResponse(Favoris favoris){
        return new FavorisResponse(
                favoris.getTrackingId(),
                favoris.getClient().getFirstName() + " " + favoris.getClient().getLastName(),
                favoris.getSong().getTitre(),
                favoris.getSong().getAlbum().getArtiste().getFirstName() + " " + favoris.getSong().getAlbum().getArtiste().getLastName() ,
                favoris.getDateCreated()
        );
    }

    public List<FavorisResponse> toResponseList(List<Favoris> list){
        return  list.stream()
                .map(this::toResponse)
                .toList() ;
    }
}
