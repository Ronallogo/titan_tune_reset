package com.titan.tune.Repositories;

import com.titan.tune.Entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public  interface AlbumRepository  extends JpaRepository<Album, Long> {

    Optional<Album> findByTrackingId(UUID trackingId);
    void deleteByTrackingId(UUID trackingId);

    @Query(value="SELECT *  FROM  albums ORDER BY album_id DESC" , nativeQuery=true)
    List<Album> getAll() ;



    @Query(value= """
            SELECT * FROM albums 
            WHERE  artiste_id = :id
            """ , nativeQuery = true)
    List<Album> getAllForOneArtiste(@Param("id") Long id) ;

}
