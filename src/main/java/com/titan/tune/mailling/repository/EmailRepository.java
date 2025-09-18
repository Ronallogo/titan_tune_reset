package com.titan.tune.mailling.repository;

import com.titan.tune.mailling.entity.EmailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailConfiguration,Long> {

    EmailConfiguration findFirstByOrderByIdAsc();
}
