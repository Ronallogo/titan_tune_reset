package com.titan.tune.Repositories;


import com.titan.tune.Entity.Playlist;
import com.titan.tune.Entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {


    @Query(value="SELECT * FROM playlists WHERE  titre ILIKE :keyword" , nativeQuery=true)
    List<Playlist> findByTitre(@Param("keyword") String keyword);

    @Query(value="SELECT  *  FROM playlists    WHERE tracking_id = :trackingId"  , nativeQuery = true)
    Optional<Playlist> findByTrackingId(@Param("trackingId") UUID trackingId);


    @Query(value= """
         SELECT  p.* FROM playlists p
         JOIN users  u ON  p.client_id = u.id
         WHERE p.client_id =  :client_id
         """ , nativeQuery = true)
    List<Playlist> findAllForOne(@Param("client_id")  Long  client_id) ;

    @Query(
            value = """
            SELECT s.* FROM songs s
            WHERE s.song_id IN
            (SELECT sp.song_id FROM song_playlists sp
            WHERE sp.playlist_id = :playlist_id ) ;
            """ , nativeQuery = true
    )
    List<Song> findAllSongForOnePlaylist(@Param("playlist_id") Long playlist_id) ;


    @Query(value= """
            SELECT * FROM playlists ORDER BY playlist_id DESC ;
            """ , nativeQuery = true)
    List<Playlist> getAll() ;



    // Map<String , Object> howManySongInPlaylist(@Param(""))
}
