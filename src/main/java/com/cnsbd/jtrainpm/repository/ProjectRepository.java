package com.cnsbd.jtrainpm.repository;

import com.cnsbd.jtrainpm.dto.IProjectUser;
import com.cnsbd.jtrainpm.dto.IUserProject;
import com.cnsbd.jtrainpm.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT t.id as id, t.name as name, t.email as email FROM Project p INNER JOIN p.members m INNER JOIN User t ON t.id = m.id WHERE p.id = :id")
    List<IProjectUser> getMembers(@Param("id") Long projectId);

    @Query(value = "SELECT t.id as id, t.createdAt as createdAt, t.status.id as statusId, t.status.title as statusName," +
            " t.members.size as memberCount, t.owner.id as ownerId, t.owner.name as ownerName, t.name as projectName," +
            " t.startDateTime as startDate, t.endDateTime as endDate, t.intro as intro, t.description as description" +
            " FROM Project t LEFT JOIN t.members m ON :uid IN m.id WHERE t.id = :id AND (t.owner.id = :uid OR :uid IN m.id)")
    Optional<IUserProject> findByProjectId(@Param("id") Long id, @Param("uid") Long uid);

    Optional<Project> findById(Long id);

    @Modifying
    @Query(value = "DELETE FROM projects_members WHERE member_of_projects_id = :pid AND members_id = :mid", nativeQuery = true)
    void removeMemberByUserId(@Param("pid") Long id, @Param("mid") Long uid);

    @Modifying(flushAutomatically = true)
    @Query(value = "INSERT INTO projects_members (members_id, member_of_projects_id) " +
            "VALUES(:uid, :pid)", nativeQuery = true)
    void addMemberByUserId(@Param("pid") Long id, @Param("uid") Long uid);

    Optional<Project> findByIdAndMembers_Id(Long id, Long memberId);

    Optional<Project> findByIdAndOwner_Id(Long id, Long userId);

    Optional<Project> findByName(String name);

    Long deleteByIdAndOwner_Id(Long id, Long ownerId);
}
