package com.titan.tune.Dto.Response;


import java.util.UUID;

public record SongPlaylistResponse(
        UUID trackingIdSongPlaylist ,
        String nomSong ,
        String nomPlaylist
) {
}
