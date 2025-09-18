package com.titan.tune.Repositories;


import com.titan.tune.Entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {




    @Query(value= """
            SELECT s.* FROM songs s
            JOIN categories c ON c.categorie_id = s.categorie_id
            WHERE c.nom_categorie LIKE CONCAT('%' , :nomCategorie , '%')
            """ , nativeQuery = true)
    List<Song> findByNomCategorieContaining(@Param("nomCategorie") String nomCategorie);


    void deleteByTrackingId(UUID trackingId);


    @Query(value= """
            SELECT * FROM  songs
            WHERE tracking_id = :trackingId
            """ , nativeQuery = true)
    Optional<Song> findByTrackingId(UUID trackingId);


    @Query(value="SELECT * FROM songs s ORDER BY  s.song_id DESC" ,  nativeQuery = true)
    List<Song> getAll();


    @Query(value="""
        SELECT s.* FROM  songs s
        WHERE  s.song_id IN (
            SELECT song_id FROM song_playlists sp
            WHERE sp.playlist_id = :playlistId AND sp.is_inside = 'true'
        )
""" , nativeQuery = true)
    List<Song> findAllByPlaylistId(@Param("playlistId") Long playlistId) ;


    @Query(value = """
            SELECT s.*
            FROM songs s
            WHERE s.album_id IN (
                SELECT a.album_id
                FROM albums a
                JOIN users u ON u.id = a.artiste_id
                WHERE u.tracking_id = :artisteTrackingId
                AND u.role = 'ARTIST'
            )
            ORDER BY s.song_id DESC;
            
            """ , nativeQuery = true)
    List<Song> findByArtisteTrackingId(@Param("artisteTrackingId") UUID artisteTrackingId);
}
