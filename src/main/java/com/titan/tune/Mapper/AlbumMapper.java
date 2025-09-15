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
                    entity.getArtiste().getFirstName() + " "+
                            entity.getArtiste().getLastName() ,
                    entity.getImageAlbum()
            ) ;
    }

    public  Album toEntity(AlbumRequest request ,
                           Artiste artiste ){


        return   new Album(
                artiste ,
                request.titreAlbum() ,
                request.imageAlbum()
        ) ;

    }


    public Album toEntityByUpdate( Album entity ,  AlbumRequest request , Artiste artiste){

        entity.setArtiste(artiste) ;
        entity.setTitreAlbum(request.titreAlbum());
        entity.setImageAlbum(request.imageAlbum());

        return  entity ;


    }

    public List<AlbumResponse> toResponseList(List<Album> listAlbum){
            return listAlbum.stream()
                    .map(this::toResponse)
                    .toList() ;
    }
}
