package com.titan.tune.Entity;


import com.titan.tune.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="SUIVRES")
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Data
public class Suivre   extends BaseEntity {

    @Column(name="id_endpoint_suivre" , unique = true ,  nullable = false , updatable = false)
    @EmbeddedId

    private Suivre_id suivreId ;
    @Column(name="is_follow" , nullable = false)
    private  boolean isFollow ;


    @Column(name="tracking_id" , nullable = false , updatable = false)
    private UUID trackingId ;

    @ManyToOne
    @JoinColumn(name="follower" , nullable = false)
    private  User follower ;
    @ManyToOne
    @JoinColumn(name="following" , nullable = false)
    private User following ;


    public Suivre(Suivre_id suivreId, boolean b, User follower, User following) {
        this.suivreId = suivreId ;
        this.isFollow = b ;
        this.follower = follower ;
        this.following = following ;
    }
}
