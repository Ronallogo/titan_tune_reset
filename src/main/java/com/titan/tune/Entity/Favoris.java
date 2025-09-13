package com.titan.tune.Entity;

import com.titan.tune.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "FAVORIS")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Favoris extends BaseEntity {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Favoris_id id;
    @Column(name="tracking_id" , nullable = false , updatable = false )
    private UUID trackingId  ;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private  User client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    public Favoris(Favoris_id favorisId, Clients client, Song song) {
        this.setId(favorisId);
        this.setSong(song);
        this.setClient(client);
    }
}
