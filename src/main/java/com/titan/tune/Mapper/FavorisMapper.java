package com.titan.tune.Mapper;


import com.titan.tune.Dto.Request.FavorisRequest;
import com.titan.tune.Dto.Response.FavorisForOneResponse;
import com.titan.tune.Dto.Response.FavorisResponse;
import com.titan.tune.Entity.Clients;
import com.titan.tune.Entity.Favoris;
import com.titan.tune.Entity.Favoris_id;
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

        return  new Favoris(
                new Favoris_id(song.getSongId() , client.getId()) ,
                client ,
                song
        );
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

    public  FavorisForOneResponse toResponseForOne(Favoris entity){
            return new FavorisForOneResponse(
                    entity.getTrackingId() ,
                    entity.getSong().getTitre() ,
                    entity.getSong().getAlbum().getArtiste().getLastName() + " " +
                            entity.getSong().getAlbum().getArtiste().getFirstName(),
                    entity.getDateUpdated()
            ) ;
    }

    public List<FavorisResponse> toResponseList(List<Favoris> list){
        return  list.stream()
                .map(this::toResponse)
                .toList() ;
    }

    public List<FavorisForOneResponse> toResponseForOne(List<Favoris> list){
        return  list.stream()
                .map(this::toResponseForOne)
                .toList() ;
    }
}
