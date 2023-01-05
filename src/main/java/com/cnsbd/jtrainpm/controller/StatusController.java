package com.cnsbd.jtrainpm.controller;

import com.cnsbd.jtrainpm.model.JsonResponse;
import com.cnsbd.jtrainpm.repository.ProjectStatusRepository;
import com.cnsbd.jtrainpm.repository.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cnsbd.jtrainpm.annotation.ApiPrefixController;

@RestController
@ApiPrefixController
public class StatusController {
    @Autowired
    private ProjectStatusRepository projectStatusRepository;
    @Autowired
    private UserStatusRepository userStatusRepository;

    @GetMapping("/user-status-list")
    public JsonResponse userStatusList() {
        return new JsonResponse(userStatusRepository.findAll());
    }

    @GetMapping("/project-status-list")
    public JsonResponse projectStatusList() {
        return new JsonResponse(projectStatusRepository.findAll());
    }
}
