package com.titan.tune.Entity;


import com.titan.tune.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ALBUMS")
public class Album extends BaseEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long albumId ;
    @Column(name="tracking_id" , nullable = false , unique = true)
    private UUID trackingId ;
    @Column(name = "titre_album"  , nullable = false )
    private String titreAlbum ;
    @Column(name = "image_album")
    private String imageAlbum ;
    @ManyToOne
    @JoinColumn(name = "artiste_id")
    private  User artiste ;


    public Album(Artiste artiste, String titre , String imageAlbum) {

        this.setArtiste(artiste);
        this.setTitreAlbum(titre);
        this.setImageAlbum(imageAlbum);
    }
}
