package com.titan.tune.Repositories;


import com.titan.tune.Entity.Song;
import com.titan.tune.Entity.SongPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SongPlaylistRepository  extends JpaRepository<SongPlaylist ,  Long> {




    @Query(value= """
           SELECT * FROM  song_playlists
           WHERE song_id = :song_id
           AND playlist_id = :playlist_id
           """ , nativeQuery = true)
    Optional<SongPlaylist> findByCombineId(
            @Param("song_id") Long song_id ,
            @Param("playlist_id") Long playlist_id);


    @Query(value= """
            SELECT s.tracking_id FROM  song_playlists s
           WHERE s.song_id = :song_id
           AND s.playlist_id = :playlist_id
           """ , nativeQuery = true)
    Optional<UUID> getTrackingId(
            @Param("song_id") Long song_id ,
            @Param("playlist_id") Long playlist_id

    ) ;


    @Query(value= """
            SELECT * FROM  songs s
             WHERE s.song_id IN (
                 SELECT DISTINCT song_id FROM song_playlists sp   \s
                 WHERE is_inside = 'true'  \s
            )
            """ , nativeQuery = true)
    List<Song> allSongInPlaylist() ;

    // @Query()
    // List<SongPlaylist> allS



    @Query(value = """
            SELECT  s.titre AS titre  , COUNT(*) AS nbrPlaylist
            FROM songs s\s
            WHERE s.song_id     IN\s
                (
                    SELECT sp.song_id FROM song_playlists sp\s
                    WHERE sp.song_id = :songId  AND is_inside = 'true'
                )
            GROUP BY titre\s
            
            """ , nativeQuery = true)
    Map<String , Object> HowManyPlaylistContainThisSong(@Param("songId") Long songId) ;
}
