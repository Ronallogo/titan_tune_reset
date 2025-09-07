package com.titan.tune.Entity;


import com.titan.tune.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/// /id, titre, utilisateur propriétaire, visibilité (privée/publique)chansons associées
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PLAYLISTS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Playlist  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId ;

    @Column(name="tracking_id")
    private UUID trackingId ;
    @Column(name="titre" , nullable = false )
    private String titre ;
    @Column(name="visibilite" , nullable = false)
    private boolean visibilite ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id" , nullable = false)
    private  User client ;

}
