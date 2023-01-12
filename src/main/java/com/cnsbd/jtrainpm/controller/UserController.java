package com.cnsbd.jtrainpm.controller;

import com.cnsbd.jtrainpm.annotation.ApiPrefixController;
import com.cnsbd.jtrainpm.dto.IUserInfo;
import com.cnsbd.jtrainpm.dto.LoginRequest;
import com.cnsbd.jtrainpm.dto.RegisterRequest;
import com.cnsbd.jtrainpm.exception.AuthFailedException;
import com.cnsbd.jtrainpm.model.JsonResponse;
import com.cnsbd.jtrainpm.model.User;
import com.cnsbd.jtrainpm.repository.UserRepository;
import com.cnsbd.jtrainpm.security.JwtUtils;
import com.cnsbd.jtrainpm.security.UserDetailsImpl;
import com.cnsbd.jtrainpm.service.UserService;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@ApiPrefixController
public class UserController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public JsonResponse items() {
        return new JsonResponse(userService.getItems());
    }

    @PostMapping("/register")
    public JsonResponse register(@RequestBody @Valid RegisterRequest body) {
        body.setPassword(encoder.encode(body.getPassword()));
        User user = userService.createUser(body);
        return new JsonResponse(new Object() {
            @JsonProperty
            private Long id = user.getId();
            @JsonProperty
            private String message = "Registration successful.";
        });
    }

    @PostMapping("/login")
    public JsonResponse login(@RequestBody @Valid LoginRequest body) throws AuthFailedException {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());
        Authentication auth = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String theJWT = jwtUtils.generateJwtToken(auth);
        UserDetailsImpl uInfo = (UserDetailsImpl) auth.getPrincipal();
        return new JsonResponse(new Object() {
            @JsonProperty
            private Object user = userService.findById(uInfo.getId());
            @JsonProperty
            private String jwt = theJWT;
            @JsonProperty
            private String message = "Login successful";
        });
    }

    @PatchMapping("/user/approve/{id}")
    public JsonResponse approve(@PathVariable("id") Long id) {
        if (userService.approve(id)) return new JsonResponse("User approved successfully.");
        throw new EntityNotFoundException("User not found");
    }

    @PatchMapping("/user/disable/{id}")
    public JsonResponse disable(@PathVariable("id") Long id) {
        if (userService.disable(id)) return new JsonResponse("User disabled successfully.");
        throw new EntityNotFoundException("User not found");
    }

    @PatchMapping("/user/enable/{id}")
    public JsonResponse enable(@PathVariable("id") Long id) {
        if (userService.enable(id)) return new JsonResponse("User enabled successfully.");
        throw new EntityNotFoundException("User not found");
    }

    @GetMapping("/users/{id}")
    public JsonResponse item(@PathVariable("id") Long userId) {
        return new JsonResponse(userService.findById(userId));
    }

    @GetMapping("/users/{id}/projects")
    public JsonResponse projects(@PathVariable("id") Long userId) {
        return new JsonResponse(userService.getProjects(userId));
    }

    @GetMapping("/who-am-i")
    public JsonResponse whoAmI() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl info = (UserDetailsImpl) auth.getPrincipal();
        return new JsonResponse(new Object() {
            @JsonProperty
            private Object user = userService.findById(info.getId());
        });
    }
}
