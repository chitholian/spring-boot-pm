package com.cnsbd.jtrainpm.service;

import com.cnsbd.jtrainpm.dto.IProjectUser;
import com.cnsbd.jtrainpm.dto.IUserProject;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<IUserProject> getItems(Long userId);

    List<IProjectUser> getMembers(Long projectId);

    Optional<IUserProject> getItem(Long id);

    Boolean addMembers(Long id, List<String> userEmails);

    Boolean removeMembers(Long id, List<Long> userIds);
}
