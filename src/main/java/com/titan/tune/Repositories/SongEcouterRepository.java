package com.titan.tune.Repositories;


import com.titan.tune.Entity.SongEcouter;
import org.springframework.stereotype.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SongEcouterRepository  extends JpaRepository<SongEcouter, Long> {



    @Query(value="SELECT * FROM  song_ecouters WHERE tracking_id = :trackingId" , nativeQuery = true)
    Optional<SongEcouter> findBytrackingId(@Param("trackingId") UUID trackingId);

    @Query(value="SELECT s.*  FROM song_ecouters s " +
            " JOIN  users u ON u.id = s.user_id " +
            "WHERE u.tracking_id = :trackingIdUser  " , nativeQuery = true)
    List<SongEcouter> findAllByTrackingIdUser(@Param("trackingIdUser") UUID trackingIdUser);

    @Query(value = "SELECT se.* FROM song_ecouters se " +
            "JOIN songs s ON se.song_id = s.song_id " +
            "WHERE LOWER(s.titre) LIKE LOWER(CONCAT('%', :title, '%')) " +
            "ORDER BY s.titre ASC",
            nativeQuery = true)
    List<SongEcouter> findByTitle(@Param("title") String title);

    @Query(value = "SELECT se.*  FROM  song_ecouters se WHERE tracking_id  = :trackingId" , nativeQuery = true)
    Optional<SongEcouter> findByTrackingId(@Param("trackingId") UUID trackingId);


    @Query(value = "SELECT COUNT(*) FROM song_ecouters " , nativeQuery = true)
    Integer totalSongEcouter();

    @Query(value = """
             SELECT s.titre AS titre ,  COUNT(se.*) AS nbrEcoute FROM song_ecouters se
            JOIN songs s ON s.song_id = se.song_id
            WHERE s.song_id =  :song_id_p
            GROUP BY titre
            """ , nativeQuery = true)
    Map<String , Object> nbrTotalEcouterForOneSong(@Param("song_id_p") Long song_id_p);


    @Query(value = """
                SELECT u.last_name AS nom , u.first_name AS prenom ,
                COUNT(*) AS total_ecoutes
                FROM song_ecouters s
                JOIN songs sg ON sg.song_id = s.song_id
                JOIN albums a ON a.album_id = sg.album_id
                JOIN  users u ON u.id  = a.artiste_id
                GROUP BY nom , prenom
                ORDER  BY total_ecoutes DESC
                LIMIT  1
            
            """ , nativeQuery = true)
    Map<String , Object> artisteMostListen() ;


    @Query(value = """
            SELECT s.titre AS  titre   ,\s
            COUNT(*) AS total_ecoutes
            FROM song_ecouters se
            JOIN songs s ON s.song_id  = se.song_id
            GROUP BY  titre
            ORDER  BY total_ecoutes DESC
            LIMIT  1
            """ , nativeQuery = true)
    Map<String  ,Object> songMostListen() ;



    @Query(value = """
            SELECT s.titre AS  titre   ,\s
            COUNT(*) AS total_liked
            FROM favoris f
            JOIN songs s ON s.song_id  = f.song_id
            WHERE s.song_id =  :song_id_p
            GROUP BY titre
            LIMIT  1 ;\s
            
            """  , nativeQuery = true)
    Map<String , Object> nbrLikeForOneSong(@Param("song_id_p")  Long song_id_p);


    @Query(value= """
          SELECT s.titre AS  titre ,   
          COUNT(*) AS total_liked
          FROM favoris f
          JOIN songs s ON s.song_id  = f.song_id
          GROUP BY titre
          ORDER BY total_liked DESC
          LIMIT  1  ;
            """ , nativeQuery = true)
    Map<String , Object> songWithMostLike();

    @Query(value= """
            
            SELECT s.titre AS titre ,  COUNT(*) AS nbrTime FROM songs s
            JOIN song_ecouters se ON se.song_id = s.song_id\s
            GROUP BY titre;
            """ , nativeQuery = true)
    List<Map<String , Object>> howMayTimeEachSongWasListen() ;


   /* @Query(value= """
            
            """ , nativeQuery = true)
    Object tauxRetention() ;*/


}
