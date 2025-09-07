package com.titan.tune.Repositories;


import com.titan.tune.Entity.Artiste;
import com.titan.tune.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ArtistRepository extends JpaRepository<Artiste, Long> {
    Optional<Artiste> findByTrackingId(UUID trackingId);

    @Query(value= """
            SELECT * FROM users
            WHERE email = :email  AND type_role = 'ARTISTE'
            """  , nativeQuery = true)
    Optional<Artiste> findByEmail(String email);

    @Query(value = """
            SELECT  *  FROM users
            WHERE type_role = 'ARTISTE'
            ORDER BY id DESC
            """ , nativeQuery = true)
    List<Artiste> findAllArtiste();

}
