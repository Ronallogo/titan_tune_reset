package com.titan.tune.Dto.Request;

import java.util.UUID;

public record SongPlaylistRequest(
        UUID trackingIdSong ,
        UUID trackingIdPlaylist
) {
}
