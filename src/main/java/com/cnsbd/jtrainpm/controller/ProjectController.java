package com.cnsbd.jtrainpm.controller;

import com.cnsbd.jtrainpm.dto.AddMembersRequest;
import com.cnsbd.jtrainpm.dto.RemoveMembersRequest;
import com.cnsbd.jtrainpm.model.JsonResponse;
import com.cnsbd.jtrainpm.service.ProjectService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cnsbd.jtrainpm.annotation.ApiPrefixController;

import javax.validation.Valid;

@ApiPrefixController
@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects/{id}")
    public JsonResponse item(@PathVariable("id") Long id) {
        return new JsonResponse(projectService.getItem(id));
    }

    @GetMapping("/projects/{id}/members")
    public JsonResponse members(@PathVariable("id") Long id) {
        return new JsonResponse(projectService.getMembers(id));
    }

    @PatchMapping("/projects/{id}/add-members")
    public JsonResponse addMembers(@PathVariable("id") Long id, @RequestBody @Valid AddMembersRequest body) {
        if (projectService.addMembers(id, body.getUserEmails())) return new JsonResponse("Members added successfully");
        return new JsonResponse(500, new Object() {
            @JsonProperty
            private String error = "Failed to add members";
        });
    }

    @DeleteMapping("/projects/{id}/remove-members")
    public JsonResponse removeMembers(@PathVariable("id") Long id, @RequestBody @Valid RemoveMembersRequest body) {
        if (projectService.removeMembers(id, body.getUserIds()))
            return new JsonResponse("Members removed successfully");
        return new JsonResponse(500, new Object() {
            @JsonProperty
            private String error = "Failed to remove members";
        });
    }
}
