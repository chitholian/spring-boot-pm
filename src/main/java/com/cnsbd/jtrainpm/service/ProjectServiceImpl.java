package com.cnsbd.jtrainpm.service;

import com.cnsbd.jtrainpm.dto.IProjectUser;
import com.cnsbd.jtrainpm.dto.IUserProject;
import com.cnsbd.jtrainpm.model.Project;
import com.cnsbd.jtrainpm.model.User;
import com.cnsbd.jtrainpm.repository.ProjectRepository;
import com.cnsbd.jtrainpm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
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
        Integer count = 0;
        for (Long uid : userIds) {
            count += projectRepository.removeMemberByUserId(id, uid);
        }
        return count > 0;
    }
}
