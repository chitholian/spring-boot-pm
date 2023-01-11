package com.cnsbd.jtrainpm.service;

import com.cnsbd.jtrainpm.dto.IUserInfo;
import com.cnsbd.jtrainpm.dto.IUserProject;
import com.cnsbd.jtrainpm.dto.LoginRequest;
import com.cnsbd.jtrainpm.dto.RegisterRequest;
import com.cnsbd.jtrainpm.exception.AuthFailedException;
import com.cnsbd.jtrainpm.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getItems();

    User createUser(RegisterRequest body);

    User login(LoginRequest body) throws AuthFailedException;

    boolean approve(Long id);

    boolean disable(Long id);

    boolean enable(Long id);

    List<IUserProject> getProjects(Long userId);

    Optional<IUserInfo> findById(Long userId);
}
