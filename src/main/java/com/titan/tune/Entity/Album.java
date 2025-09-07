package com.titan.tune.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ALBUMS")
public class Album {
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
    private Artiste artiste ;



}
