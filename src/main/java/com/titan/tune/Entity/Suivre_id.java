package com.titan.tune.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Suivre_id {


    @Column(name="follower_id" , nullable = false)
    private   Long followerId ;

    @Column(name="following_id" , nullable = false)
    private  Long followingId ;
}
