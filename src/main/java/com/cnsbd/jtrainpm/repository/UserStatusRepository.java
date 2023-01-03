package com.cnsbd.jtrainpm.repository;

import com.cnsbd.jtrainpm.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {
}
