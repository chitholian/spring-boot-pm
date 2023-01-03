package com.cnsbd.jtrainpm.service;

import com.cnsbd.jtrainpm.model.Project;
import com.cnsbd.jtrainpm.model.Role;
import com.cnsbd.jtrainpm.model.User;
import com.cnsbd.jtrainpm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Override
    public List<Project> getItems() {
        return null;
    }
}
