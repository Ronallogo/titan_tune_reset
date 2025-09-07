package com.titan.tune.Service;


import com.titan.tune.Dto.Request.PlaylistRequest;
import com.titan.tune.Dto.Request.SongPlaylistRequest;
import com.titan.tune.Dto.Response.PlaylistResponse;
import com.titan.tune.Dto.Response.SongPlaylistResponse;
import com.titan.tune.Dto.Response.SongResponse;
import com.titan.tune.Entity.Playlist;
import com.titan.tune.Entity.Song;

import java.util.List;
import java.util.UUID;

public interface PlaylistService {
    SongPlaylistResponse addSongToPlaylist(SongPlaylistRequest request) ;
    void removeSongToPlaylist(UUID trackingIdPlaylist , UUID trackingIdSong);

    List<SongResponse> getAllSongForPlaylist(UUID trackingIdPlaylist) ;

    List<PlaylistResponse> getAllPlaylistForOneSong(UUID trackingIdSong) ;

    PlaylistResponse create(PlaylistRequest playlist, UUID trackingIdClientParam);

    List<PlaylistResponse> getAllForOne(UUID trackingIdParam) ;


    boolean ifSongAlreadyInPlaylist(Song song, Playlist playlist);

    PlaylistResponse update(PlaylistRequest playlist,
                            UUID trackingIdClientParam,
                            UUID trackingIdPlaylistPram);



    PlaylistResponse get(UUID trackingIdParam);

    List<PlaylistResponse> getAll();
    void delete(UUID trackingIdParam);

    List<PlaylistResponse> findByTitle(String title) ;


    PlaylistResponse changeVisibility(UUID trackingIdPlaylist) ;

    List<SongResponse> allSongForOnePlaylist(UUID tracking_id_playlist) ;
}
