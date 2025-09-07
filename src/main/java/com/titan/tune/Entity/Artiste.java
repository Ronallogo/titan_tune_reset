package com.titan.tune.Entity;


import com.titan.tune.utils.TypeRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("ARTISTES")
public class Artiste extends User implements Serializable {
    private String description;
    private  String alias ;

    public Artiste(String firstName,
                   String lastName,
                   String email, String password , String phone){
        super(firstName,lastName,email,password,phone);
        this.description = description;
        this.alias = alias;
        this.setRole(TypeRole.ARTIST);
    }

}