package com.titan.tune.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongEcouter_id {


    @Column(name = "_user" , nullable = false)
    private   Long  userId ;


    @Column(name = "song" , nullable = false)
    private    Long   SongId ;

}
