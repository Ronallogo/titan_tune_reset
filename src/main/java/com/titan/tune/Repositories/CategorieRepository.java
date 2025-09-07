package com.titan.tune.Repositories;


import com.titan.tune.Entity.Categorie;
import com.titan.tune.Entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findByTrackingId(UUID trackingId);


    void deleteByTrackingId(UUID trackingId);


    @Query(value="SELECT * FROM categories  ORDER BY categorie_id DESC" , nativeQuery = true)
    List<Categorie> getAll() ;


    @Query(value= """
                SELECT * FROM categories
                WHERE nom_categorie = :name
            """ , nativeQuery = true)
    List<Categorie> findCategorieByName(@Param("name") String name);
}