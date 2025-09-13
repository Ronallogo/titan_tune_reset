package com.titan.tune.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Favoris_id {


    @Column(name = "song" , nullable = false)
    private Long song ;
    @Column(name = "client" , nullable = false)
    private Long Client ;
}
