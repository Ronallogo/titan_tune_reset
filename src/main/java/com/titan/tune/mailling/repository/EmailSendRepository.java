package com.titan.tune.mailling.repository;


import com.titan.tune.mailling.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSendRepository extends JpaRepository<Email,Long>  {
}
