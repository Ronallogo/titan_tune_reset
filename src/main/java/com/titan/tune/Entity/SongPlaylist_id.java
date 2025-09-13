package com.titan.tune.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongPlaylist_id {

    @Column(name="song" , nullable = false , updatable = false)
    private  Long songId ;

    @Column(name = "playlist" , nullable = false , updatable = false)
    private  Long playlistId ;
}
