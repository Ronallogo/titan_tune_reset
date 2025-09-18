package com.titan.tune.Entity;


import com.titan.tune.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name="SONGS")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"Album","Categorie"})

public class Song extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SongId ;
    @Column(name="tracking_id" , unique = true , nullable = false)
    private UUID trackingId ;
    @Column(nullable = false)
    private String titre ;
    @Column(nullable = false)
    private String audio ;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;


    public Song(String titre, Categorie categorie, String string, Album album) {
        this.setAlbum(album);
        this.setCategorie(categorie);
        this.setTitre(titre);
        this.setAudio(string);
    }
}
