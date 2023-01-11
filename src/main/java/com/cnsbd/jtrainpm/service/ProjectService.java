package com.cnsbd.jtrainpm.service;

import com.cnsbd.jtrainpm.dto.CreateProjectRequest;
import com.cnsbd.jtrainpm.dto.IProjectUser;
import com.cnsbd.jtrainpm.dto.IUserProject;
import com.cnsbd.jtrainpm.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<IUserProject> getItems(Long userId);

    List<IProjectUser> getMembers(Long projectId);

    Optional<IUserProject> getItem(Long id);

    Boolean addMembers(Long id, List<String> userEmails);

    Boolean removeMembers(Long id, List<Long> userIds);

    Boolean startNow(Long id);

    Boolean endNow(Long id);

    Project createProject(CreateProjectRequest body);

    Boolean deleteItem(Long id);

    void printItemList();

    Boolean updateItem(Long id, CreateProjectRequest body);
}
