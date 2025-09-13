package com.titan.tune.Mapper;


import com.titan.tune.Dto.Request.SongEcouterRequest;
import com.titan.tune.Dto.Response.SongEcouterForOneUser;
import com.titan.tune.Dto.Response.SongEcouterResponse;
import com.titan.tune.Entity.Song;
import com.titan.tune.Entity.SongEcouter;
import com.titan.tune.Entity.SongEcouter_id;
import com.titan.tune.Entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SongEcouterMapper {

    public SongEcouter toEntity(
            Song song ,
            User user
    ){
        return new SongEcouter(
                new SongEcouter_id(
                        song.getSongId() ,
                        user.getId()
                ) ,
                song ,
                user

        );


    }



    public SongEcouterResponse toResponse(SongEcouter entity){
        return  new SongEcouterResponse(
                entity.getTrackingId() ,
                entity.getUser().getEmail() ,
                entity.getSong().getTitre()
        );
    }

    public SongEcouterForOneUser toResponseSearch(SongEcouter entity){
        return new  SongEcouterForOneUser(
                entity.getTrackingId() ,
                entity.getSong().getTitre() ,
                entity.getSong().getAlbum().getArtiste().getLastName() + " " +
                        entity.getSong().getAlbum().getArtiste().getFirstName(),
                entity.getSong().getAlbum().getTitreAlbum()
        );
    }


    public List<SongEcouterResponse> toResponseList(List<SongEcouter> list){
        return list.stream()
                .map(this::toResponse)
                .toList() ;
    }

    public List<SongEcouterForOneUser> toResponseListForOne(List<SongEcouter> list){
        return list.stream()
                .map(this::toResponseSearch)
                .toList() ;
    }







}
