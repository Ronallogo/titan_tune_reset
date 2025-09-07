package com.titan.tune.Mapper;

import com.titan.tune.Dto.Request.SongRequest;
import com.titan.tune.Dto.Response.SongResponse;
import com.titan.tune.Entity.Album;
import com.titan.tune.Entity.Artiste;
import com.titan.tune.Entity.Categorie;
import com.titan.tune.Entity.Song;
import org.hibernate.sql.ast.tree.cte.CteColumn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class SongMapper {


    public   Song toEntity(
            SongRequest request ,
            Album album ,
            Categorie categorie
        ) {

        Song song = new Song();
        song.setTitre(request.titre());
        song.setAudio(String.valueOf(request.audio()));
        song.setCategorie(categorie);
        song.setAlbum(album);
        song.setTrackingId(UUID.randomUUID());

        return song;

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
                entity.getAlbum().getArtiste().getAlias()
        );
    }

    public List<SongResponse> toResponseList(List<Song> list){
        return  list.stream()
                .map(this::toResponse)
                .toList() ;
    }
}
