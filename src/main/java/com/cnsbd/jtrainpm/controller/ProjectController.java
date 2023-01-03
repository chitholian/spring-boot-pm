package com.cnsbd.jtrainpm.controller;

import com.cnsbd.jtrainpm.model.JsonResponse;
import com.cnsbd.jtrainpm.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public JsonResponse items() {
        return new JsonResponse(projectService.getItems());
    }
}
