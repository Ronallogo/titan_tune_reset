package com.titan.tune.Repositories;

import com.titan.tune.Entity.Clients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Clients, Long> {

    @Query(value= """
            SELECT * FROM  users
            WHERE tracking_id = :trackingId AND
            role = 'CLIENT'
            """ , nativeQuery=true)
    Optional<Clients> findByTrackingId(UUID trackingId);
}