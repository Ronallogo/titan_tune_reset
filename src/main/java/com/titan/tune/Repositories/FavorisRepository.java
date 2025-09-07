package com.titan.tune.Repositories;

import com.titan.tune.Entity.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavorisRepository extends JpaRepository<Favoris, Long> {

    @Query("select r from Favoris r order by r.id desc")
    List<Favoris> findAllByFavoris();

    Optional<Favoris> findByTrackingId(UUID trackingId);

    @Query(value= """
               SELECT * FROM  favoris f
               WHERE f.client = :client_id
            """ , nativeQuery = true)
     List<Favoris> favorisForOneClient(@Param("client_id") Long client_id);


}
