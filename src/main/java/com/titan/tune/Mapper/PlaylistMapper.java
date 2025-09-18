package com.titan.tune.Mapper;


import com.titan.tune.Dto.Request.PlaylistRequest;
import com.titan.tune.Dto.Response.PlaylistResponse;
import com.titan.tune.Entity.Clients;
import com.titan.tune.Entity.Playlist;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaylistMapper {

    public PlaylistResponse toResponse(Playlist playlist){

        return  new PlaylistResponse(
                playlist.getTitre(),
                playlist.isVisibilite(),
                playlist.getClient().getFirstName() + " " + playlist.getClient().getLastName()
        ) ;

    }

    public Playlist toEntity(PlaylistRequest request){

        return  Playlist.builder()
                .titre(request.titre())
                .visibilite(request.visibilite())
                .build();

    }


    public Playlist toEntityByUpdate(
            PlaylistRequest request ,
            Playlist entity ,
            Clients client
    ){
            entity.setTitre(request.titre());
            entity.setClient(client) ;
            return entity ;

    }


    public List<PlaylistResponse> toResponseList(List<Playlist> list){
        return  list
                .stream()
                .map(this::toResponse)
                .toList() ;
    }

    public PlaylistResponse toResponseForOneClient(Playlist  playlist){
        return  new PlaylistResponse(
                playlist.getTitre(),
                playlist.isVisibilite(),
                playlist.getClient().getFirstName() + " " + playlist.getClient().getLastName()
        ) ;
    }
}
