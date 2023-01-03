package com.cnsbd.jtrainpm.service;

import com.cnsbd.jtrainpm.dto.LoginDTO;
import com.cnsbd.jtrainpm.dto.RegisterDTO;
import com.cnsbd.jtrainpm.exception.AuthFailedException;
import com.cnsbd.jtrainpm.model.Role;
import com.cnsbd.jtrainpm.model.User;
import com.cnsbd.jtrainpm.model.UserStatus;
import com.cnsbd.jtrainpm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    public List<User> getItems() {
        return userRepo.findAll();
    }

    @Override
    public User createUser(RegisterDTO body) {
        Role role = new Role();
        role.setId(Role.DEVELOPER);

        User user = new User();
        user.setApproved(false);
        user.setName(body.getName());
        user.setEmail(body.getEmail());
        user.setPassword(body.getPassword());
        user.setStatus(UserStatus.getDefault());
        user.setRole(role);

        return userRepo.save(user);
    }

    @Override
    public User login(LoginDTO body) throws AuthFailedException {
        User user = userRepo.findByUsername(body.getUsername());
        if (user == null) user = userRepo.findByEmail(body.getUsername());
        if (user == null) throw new AuthFailedException("User not found.");
        if (!Objects.equals(user.getPassword(), body.getPassword()))
            throw new AuthFailedException("Password is invalid.");
        if (!user.getApproved())
            throw new AuthFailedException("User is not approved, contact admin.");
        if (!Objects.equals(user.getStatus().getId(), UserStatus.ACTIVE))
            throw new AuthFailedException("User is inactive.");
        return user;
    }

    @Override
    @Transactional
    public boolean approve(Long id) {
        return userRepo.approveById(id) > 0;
    }

    @Override
    @Transactional
    public boolean disable(Long id) {
        return userRepo.disableById(id) > 0;
    }

    @Override
    @Transactional
    public boolean enable(Long id) {
        return userRepo.enableById(id) > 0;
    }
}
