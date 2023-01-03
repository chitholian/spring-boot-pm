package com.cnsbd.jtrainpm.repository;

import com.cnsbd.jtrainpm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE User SET approved = true, status = 1 WHERE id = :id")
    int approveById(@Param("id") Long id);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE User SET status = 2 WHERE id = :id")
    int disableById(@Param("id") Long id);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE User SET status = 1 WHERE id = :id")
    int enableById(@Param("id") Long id);
}
