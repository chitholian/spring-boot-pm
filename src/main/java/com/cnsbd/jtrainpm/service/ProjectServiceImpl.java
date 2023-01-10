package com.cnsbd.jtrainpm.service;

import com.cnsbd.jtrainpm.dto.CreateProjectRequest;
import com.cnsbd.jtrainpm.dto.IProjectUser;
import com.cnsbd.jtrainpm.dto.IUserProject;
import com.cnsbd.jtrainpm.model.Project;
import com.cnsbd.jtrainpm.model.ProjectStatus;
import com.cnsbd.jtrainpm.model.User;
import com.cnsbd.jtrainpm.repository.ProjectRepository;
import com.cnsbd.jtrainpm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<IUserProject> getItems(Long userId) {
        return userRepository.getProjects(userId);
    }

    @Override
    public List<IProjectUser> getMembers(Long projectId) {
        return projectRepository.getMembers(projectId);
    }

    @Override
    public Optional<IUserProject> getItem(Long id) {
        return projectRepository.findByProjectId(id);
    }

    @Override
    @Transactional
    public Boolean addMembers(Long id, List<String> userEmails) {
        int count = 0;
        for (String email : userEmails) {
            User user = userRepository.findByEmail(email);
            if (user == null) continue;
            if (projectRepository.findByIdAndMembers_Id(id, user.getId()).isPresent()) continue;
            if (projectRepository.findByIdAndOwner_Id(id, user.getId()).isPresent()) continue;
            projectRepository.addMemberByUserId(id, user.getId());
            count++;
        }
        return count > 0;
    }

    @Override
    @Transactional
    public Boolean removeMembers(Long id, List<Long> userIds) {
        int count = 0;
        System.err.println(id + ":" + userIds);
        for (Long uid : userIds) {
            projectRepository.removeMemberByUserId(id, uid);
            count++;
        }
        return count > 0;
    }

    @Override
    public Boolean startNow(Long id) {
        Optional<Project> op = projectRepository.findById(id);
        if (!op.isPresent()) throw new EntityNotFoundException("Project not found");
        Project p = op.get();
        if (!Objects.equals(p.getStatus().getId(), ProjectStatus.PRE)) return false;
        p.setStatus(new ProjectStatus(ProjectStatus.STARTED, "Started"));
        p.setStartDateTime(new Date());
        projectRepository.saveAndFlush(p);
        return true;
    }

    @Override
    public Boolean endNow(Long id) {
        Optional<Project> op = projectRepository.findById(id);
        if (!op.isPresent()) throw new EntityNotFoundException("Project not found");
        Project p = op.get();
        if (!Objects.equals(p.getStatus().getId(), ProjectStatus.STARTED)) return false;
        p.setStatus(new ProjectStatus(ProjectStatus.ENDED, "Ended"));
        p.setEndDateTime(new Date());
        projectRepository.saveAndFlush(p);
        return true;
    }

    @Override
    public Project createProject(CreateProjectRequest body) {
        // Check duplicate name.
        if (projectRepository.findByName(body.getName()).isPresent())
            throw new EntityExistsException("Project name already exists");
        Project p = new Project(null, body.getName(), body.getIntro(), body.getDescription(), null, null, null, null, null);
        if (body.getStartNow()) {
            p.setStartDateTime(new Date());
            p.setStatus(new ProjectStatus(ProjectStatus.STARTED, "Started"));
        } else {
            p.setStatus(new ProjectStatus(ProjectStatus.PRE, "Pre"));
        }
        p.setOwner(User.builder().id(1L).build());
        projectRepository.saveAndFlush(p);
        return p;
    }

    @Override
    @Transactional
    public Boolean deleteItem(Long id) {
        Long count = projectRepository.deleteByIdAndOwner_Id(id, 1L);
        return count > 0;
    }
}
