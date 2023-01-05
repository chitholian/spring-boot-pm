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
    @Query("SELECT t.id as id, t.name as name, t.email as email FROM Project p INNER JOIN p.members m INNER JOIN User t ON t.id = m.id")
    List<IProjectUser> getMembers(@Param("id") Long projectId);

    @Query(value = "SELECT t.id as id, t.status.id as statusId, t.status.title as statusName," +
            " t.members.size as memberCount, t.owner.id as ownerId, t.owner.name as ownerName, t.name as projectName," +
            " t.startDateTime as startDate, t.endDateTime as endDate, t.intro as intro, t.description as description" +
            " FROM Project t WHERE t.id = :id")
    Optional<IUserProject> findByProjectId(@Param("id") Long id);

    Optional<Project> findById(Long id);

    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM projects_members WHERE project_id = :pid AND members_id = :mid", nativeQuery = true)
    Integer removeMemberByUserId(@Param("pid") Long id, @Param("mid") Long uid);

    @Modifying(flushAutomatically = true)
    @Query(value = "INSERT INTO projects_members (project_id, members_id, member_of_projects_id) " +
            "VALUES(:pid, :uid, :pid)", nativeQuery = true)
    void addMemberByUserId(@Param("pid") Long id, @Param("uid") Long uid);

    Optional<Project> findByIdAndMembers_Id(Long id, Long memberId);

    Optional<Project> findByIdAndOwner_Id(Long id, Long userId);
}
