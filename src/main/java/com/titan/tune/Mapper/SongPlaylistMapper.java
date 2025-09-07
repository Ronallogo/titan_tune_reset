package com.titan.tune.Mapper;


import com.titan.tune.Dto.Request.SongPlaylistRequest;
import com.titan.tune.Dto.Response.SongPlaylistResponse;
import com.titan.tune.Entity.Playlist;
import com.titan.tune.Entity.Song;
import com.titan.tune.Entity.SongPlaylist;
import com.titan.tune.Entity.SongPlaylist_id;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SongPlaylistMapper {

    public SongPlaylistResponse toResponse(SongPlaylist entity){
        return new SongPlaylistResponse(
                entity.getTrackingId() ,
                entity.getPlaylist().getTitre() ,
                entity.getSong().getTitre()
        ) ;
    }



    public SongPlaylist toEntity(

            Song song ,
            Playlist playlist
    ){
        return  SongPlaylist.builder()
                .songPlaylistId(
                        SongPlaylist_id.builder()
                                .playlistId(playlist.getPlaylistId())
                                .songId(song.getSongId())
                                .build()
                )
                .playlist(playlist)
                .isInside(true)
                .song(song)
                .build() ;
    }

    public List<SongPlaylistResponse> toResponseList(List<SongPlaylist> list){
        return  list.stream()
                .map(this::toResponse)
                .toList() ;
    }
}
