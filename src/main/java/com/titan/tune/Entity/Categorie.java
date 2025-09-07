package com.titan.tune.Entity;

import com.titan.tune.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="CATEGORIES")
public class Categorie  extends BaseEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long categorieId ;
    @Column(name="tracking_id" , nullable = false , unique = true)
    private UUID trackingId ;
    @Column(name = "nom_categorie"  , nullable = false )
    private String nomCategorie ;


}
