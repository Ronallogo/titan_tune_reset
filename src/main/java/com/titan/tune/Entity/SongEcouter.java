package com.titan.tune.Entity;

import com.titan.tune.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.util.UUID;

@Entity
@Table(name="SONG_ECOUTERS")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongEcouter  extends BaseEntity {


    @EmbeddedId
    @Column(name = "id", nullable = false)
    private  SongEcouter_id songEcouterId ;


    @Column(name="tracking_id" , nullable = false , updatable = false)
    private UUID trackingId ;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private  User  user ;

    @ManyToOne
    @JoinColumn(name = "song_id" , nullable = false)
    private   Song   Song ;

    @JoinColumn(name = "nbr_listening" , nullable = false)
    private Integer nbrListening ;


    public SongEcouter(SongEcouter_id songEcouterId, Song song, User user) {

        this.setSongEcouterId(songEcouterId);
        this.setSong(song);
        this.setUser(user);
        this.setNbrListening(this.getNbrListening() + 1);
    }
}
