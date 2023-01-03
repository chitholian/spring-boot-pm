package com.cnsbd.jtrainpm.service;

import com.cnsbd.jtrainpm.dto.LoginDTO;
import com.cnsbd.jtrainpm.dto.RegisterDTO;
import com.cnsbd.jtrainpm.exception.AuthFailedException;
import com.cnsbd.jtrainpm.model.User;

import java.util.List;

public interface UserService {
    List<User> getItems();

    User createUser(RegisterDTO body);

    User login(LoginDTO body) throws AuthFailedException;

    boolean approve(Long id);

    boolean disable(Long id);

    boolean enable(Long id);
}
