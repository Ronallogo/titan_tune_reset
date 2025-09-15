package com.titan.tune.Entity;


import com.titan.tune.utils.TypeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serializable;


@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor

@DiscriminatorValue("ARTIST")
public class Artiste extends User implements Serializable {

    @Column(name = "description" , length = 200)
    private String description;

    @Column(name = "alias"  , length = 50)
    private  String alias ;


}