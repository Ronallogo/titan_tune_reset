package com.titan.tune.Repositories;

import com.titan.tune.Entity.Suivre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuivreRepository extends JpaRepository<Suivre, Long> {


    @Query(value="SELECT * FROM suivres " +
            "WHERE follower_user_id = :follower_id " +
            "AND following_user_id = :following_id" , nativeQuery=true)
    Optional<Suivre> findByTrackingIdEmbeddable(
            @Param("follower_id")  Long follower_id ,
            @Param("following_id")  Long following_id
    );



    @Query(value="SELECT COUNT(*) FROM suivres WHERE  following = :artiste AND is_follow = true" ,nativeQuery = true)
    Integer nbrFollowers(@Param("artiste") Long  artiste);

    @Query(value="SELECT COUNT(*) FROM suivres WHERE  follower = :follower AND is_follow = true" ,nativeQuery = true)
    Integer nbrFollowing(@Param("follower") Long   follower);

    @Query(value="SELECT * FROM suivres WHERE  following = :artiste AND is_follow = true" ,nativeQuery = true)
    List<Suivre> followers(@Param("artiste")  Long  artiste);

    @Query(value="SELECT * FROM suivres WHERE follower  = :follower AND is_follow = true" , nativeQuery=true)
    List<Suivre> following(@Param("follower")  Long  follower);
}
