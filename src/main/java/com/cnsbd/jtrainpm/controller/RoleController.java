package com.cnsbd.jtrainpm.controller;

import com.cnsbd.jtrainpm.model.JsonResponse;
import com.cnsbd.jtrainpm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cnsbd.jtrainpm.annotation.ApiPrefixController;


@RestController
@ApiPrefixController
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/roles")
    public JsonResponse items() {
        return new JsonResponse(roleRepository.findAll());
    }
}
