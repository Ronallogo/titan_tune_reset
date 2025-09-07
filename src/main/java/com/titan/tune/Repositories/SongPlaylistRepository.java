package com.titan.tune.Repositories;


import com.titan.tune.Entity.SongPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SongPlaylistRepository  extends JpaRepository<SongPlaylist ,  Long> {




    @Query(value= """
           SELECT * FROM  song_playlist
           WHERE song_id = :song_id
           AND playlist_id = :playlist_id
           """ , nativeQuery = true)
    Optional<SongPlaylist> findByCombineId(
            @Param("song_id") Long song_id ,
            @Param("playlist_id") Long playlist_id);


    @Query(value= """
            SELECT s.tracking_id FROM  song_playlist s
           WHERE s.song_id = :song_id
           AND s.playlist_id = :playlist_id
           """ , nativeQuery = true)
    Optional<UUID> getTrackingId(
            @Param("song_id") Long song_id ,
            @Param("playlist_id") Long playlist_id

    ) ;

    // @Query()
    // List<SongPlaylist> allS
}
