package com.titan.tune.Repositories;

import com.titan.tune.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByTrackingId(UUID trackingId);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u  order by u.id desc" )
    List<User> findAllUser();


    @Query(value= """
            SELECT * FROM users
            WHERE role = 'ARTIST'
            ORDER BY id DESC
            
            """ , nativeQuery = true)
    List<User> findAllArtist();


    @Query(value= """
            SELECT * FROM  users
            WHERE role = 'CLIENT'
            ORDER BY id DESC
            """ , nativeQuery = true)
    List<User> findAllClient();



    @Query(value = """
            SELECT * FROM users
            WHERE role = 'CLIENT' AND tracking_id = :trackingId
            ORDER BY id DESC
            """ , nativeQuery = true)
    Optional<User> findClient(@Param("trackingId")UUID trackingId);



    @Query(value= """
            SELECT * FROM users
            WHERE role = 'ARTIST' AND tracking_id = :trackingId
            ORDER BY id DESC
            """ , nativeQuery = true)
    Optional<User> findArtist(@Param("trackingId")UUID trackingId);



    @Query(value= """
            SELECT * FROM  users
            ORDER BY id DESC
            """ , nativeQuery = true)
    List<User> getAll()  ;

    @Query(value="SELECT COUNT(*)  FROM  users WHERE  actif = true" , nativeQuery = true)
    Integer totalUserActif();



}
