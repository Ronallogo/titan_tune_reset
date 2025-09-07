package com.titan.tune.Mapper;


import com.titan.tune.Dto.Request.AlbumRequest;
import com.titan.tune.Dto.Response.AlbumResponse;
import com.titan.tune.Entity.Album;
import com.titan.tune.Entity.Artiste;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AlbumMapper {



    public AlbumResponse toResponse(Album entity){
            return    new AlbumResponse(
                    entity.getTrackingId()  ,
                    entity.getTitreAlbum(),
                    entity.getArtiste().getAlias()
            ) ;
    }

    public  Album toEntity(AlbumRequest request ,
                           Artiste artiste ){
        Album entity = new Album() ;

        entity.setArtiste(artiste) ;
        entity.setTitreAlbum(request.titreAlbum());
        entity.setTrackingId(UUID.randomUUID());

        return  entity ;

    }


    public Album toEntityByUpdate( Album entity ,  AlbumRequest request , Artiste artiste){

        entity.setArtiste(artiste) ;
        entity.setTitreAlbum(request.titreAlbum());

        return  entity ;


    }

    public List<AlbumResponse> toResponseList(List<Album> listAlbum){
            return listAlbum.stream()
                    .map(this::toResponse)
                    .toList() ;
    }
}
