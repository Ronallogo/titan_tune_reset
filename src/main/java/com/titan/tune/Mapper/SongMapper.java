package com.titan.tune.Mapper;

import com.titan.tune.Dto.Request.SongRequest;
import com.titan.tune.Dto.Response.SongResponse;
import com.titan.tune.Entity.Album;
import com.titan.tune.Entity.Artiste;
import com.titan.tune.Entity.Categorie;
import com.titan.tune.Entity.Song;
import org.hibernate.sql.ast.tree.cte.CteColumn;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@Component
public class SongMapper {


    public   Song toEntity(
            SongRequest request ,
            Album album ,
            Categorie categorie
        ) {

        return  new Song(
                request.titre() ,
                categorie  ,
                request.audio().toString() ,
                album
        ) ;



    }

    public  Song toEntityUpdate(
            SongRequest  request,
            Song entity ,
            Album album ,
            Categorie categorie

        ) {


        entity.setTitre(request.titre());
        entity.setCategorie(categorie) ;
        entity.setAlbum(album);
        return entity  ;
    }

    public SongResponse toResponse(Song entity) {
        return new SongResponse(
                entity.getTrackingId(),
                entity.getTitre(),
                entity.getAudio(),
                entity.getAlbum().getArtiste().getFirstName() +" "+
                        entity.getAlbum().getArtiste().getLastName()
        );
    }

    public List<SongResponse> toResponseList(List<Song> list){
        return  list.stream()
                .map(this::toResponse)
                .toList() ;
    }
}
