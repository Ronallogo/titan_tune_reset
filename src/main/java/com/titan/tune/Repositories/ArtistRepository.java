package com.titan.tune.Repositories;


import com.titan.tune.Entity.Artiste;
import com.titan.tune.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ArtistRepository extends JpaRepository<Artiste, Long> {


    @Query(value= """
            SELECT * FROM users
            WHERE tracking_id = :trackingId AND role  = 'ARTIST'
            """ , nativeQuery = true)
    Optional<Artiste> findByTrackingId(@Param("trackingId") UUID trackingId);

    @Query(value= """
            SELECT * FROM users
            WHERE email = :email  AND role = 'ARTIST'
            """  , nativeQuery = true)
    Optional<Artiste> findByEmail(@Param("email")String email);

    @Query(value = """
            SELECT  *  FROM users
            WHERE role = 'ARTISTE'
            ORDER BY id DESC
            """ , nativeQuery = true)
    List<Artiste> findAllArtiste();

}
