package com.titan.tune.Entity;

import com.titan.tune.utils.BaseEntity;
import com.titan.tune.utils.TypeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Entity
@Table(name="USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "tracking_id" , nullable = false , unique = true)
    private  UUID trackingId ;

    @Column(nullable = false)
    private  String firstName ;

    @Column(nullable = false)
    private  String lastName ;

    @Column(nullable = false)
    private  String phone ;

    @Column(nullable = false , unique = true)
    private  String email ;

    @Column(nullable = false)
    private  String password ;

    @Enumerated(EnumType.STRING)
    private TypeRole role ;

    private boolean actif = false;

    public User(String firstName, String lastName, String email, String password, String phone) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(Long id,
                String firstName,
                String lastName,
                String email,
                String password,
                String phone,
                UUID trackingId,
                TypeRole role,
                boolean actif) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.trackingId = trackingId;
        this.role = role;
        this.actif = actif;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("Role" + this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
