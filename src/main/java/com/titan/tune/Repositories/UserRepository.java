package com.titan.tune.Repositories;

import com.titan.tune.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByTrackingId(UUID trackingId);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u  order by u.id desc" )
    List<User> findAllUser();



}
