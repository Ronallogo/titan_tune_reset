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
            JOIN categories c ON 
            WHERE 
            """ , nativeQuery = true)
    List<Song> findByNomCategorieContaining(String nomCategorie);


    void deleteByTrackingId(UUID trackingId);


    @Query(value= """
            SELECT * FROM  song
            WHERE tracking_id = :trackingId
            """ , nativeQuery = true)
    Optional<Song> findByTrackingId(UUID trackingId);


    @Query(value="SELECT * FROM songs ORDER BY  s.song_id DESC" ,  nativeQuery = true)
    List<Song> getAll();


    @Query(value = """
            SELECT s.*
            FROM songs s
            WHERE s.album_id IN (
                SELECT a.album_id
                FROM albums a
                JOIN users u ON u.user_id = a.artiste_id
                WHERE u.tracking_id = :artisteTrackingId
                AND u.type_role = 'ARTISTE'
            )
            ORDER BY s.song_id DESC;
            
            """ , nativeQuery = true)
    List<Song> findByArtisteTrackingId(@Param("artisteTrackingId") UUID artisteTrackingId);
}
