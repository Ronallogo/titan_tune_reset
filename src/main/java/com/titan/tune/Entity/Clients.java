package com.titan.tune.Entity;

import com.titan.tune.utils.TypeRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CLIENTS")
public class Clients extends User{

    public Clients(String FirstName, String LastName,String Email, String Password , String Phone){
        super(FirstName,LastName,Email,Password,Phone);
        this.setRole(TypeRole.CLIENT);
    }
}
