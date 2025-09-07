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
@DiscriminatorValue("ADMIN")
public class Admin extends User {

   public Admin(String FirstName, String LastName, String Email, String Password , String Phone){
       super(FirstName,LastName,Email,Password,Phone);
       this.setRole(TypeRole.ADMIN);
   }


}
