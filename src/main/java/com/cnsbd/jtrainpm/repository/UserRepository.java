package com.cnsbd.jtrainpm.repository;

import com.cnsbd.jtrainpm.dto.IUserInfo;
import com.cnsbd.jtrainpm.dto.IUserProject;
import com.cnsbd.jtrainpm.dto.IUserProjectCounts;
import com.cnsbd.jtrainpm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    @Query(value = "SELECT t.id as id, t.status.id as statusId, t.status.title as statusName," +
            " t.members.size as memberCount, t.owner.id as ownerId, t.owner.name as ownerName, t.name as projectName," +
            " t.startDateTime as startDate, t.endDateTime as endDate, t.intro as intro, t.description as description" +
            " FROM Project t LEFT JOIN t.members m ON :uid IN m.id WHERE t.owner.id = :uid OR :uid IN m.id")
    List<IUserProject> getProjects(@Param("uid") Long userId);

    @Query("SELECT u.id as id, u.name as name, u.email as email, u.username as username, u.status.id as statusId, " +
            "u.status.title as statusName, u.role.id as roleId, u.role.title as roleName, " +
            "u.projects.size as projectCount, u.memberOfProjects.size as contribCount " +
            "FROM User u WHERE u.id = :id")
    Optional<IUserInfo> findByUserId(@Param("id") Long userId);

    @Query("SELECT u.projects.size as createdItems, u.memberOfProjects.size as contributedItems FROM User u WHERE u.id = :uid")
    IUserProjectCounts getUserProjectCounts(@Param("uid") Long uid);
}
