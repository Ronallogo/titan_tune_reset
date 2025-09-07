package com.titan.tune.Repositories;


import com.titan.tune.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.titan.tune.Entity.Jwt;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends JpaRepository<Jwt, Long> {

    Optional<Object> findByvaleurAndDesactiveAndExpired(String valeur, boolean desactive, boolean expired);

    @Query("FROM Jwt  j WHERE j.expired = :expire  AND j.desactive = :desactive AND j.user.email = :email")
    Optional<Object> findUserValidToken(String email, boolean desactive, boolean expire);

    @Query("FROM Jwt  j WHERE j.user.email = :email")
    Stream<Jwt> findUser(String email);

    void deleteAllByExpiredAndDesactive( boolean expired, boolean desactive);

    void deleteByUser(User user);
}
