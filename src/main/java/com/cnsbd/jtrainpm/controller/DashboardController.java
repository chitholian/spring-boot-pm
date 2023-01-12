package com.cnsbd.jtrainpm.controller;

import com.cnsbd.jtrainpm.annotation.ApiPrefixController;
import com.cnsbd.jtrainpm.dto.IUserProjectCounts;
import com.cnsbd.jtrainpm.model.JsonResponse;
import com.cnsbd.jtrainpm.repository.UserRepository;
import com.cnsbd.jtrainpm.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiPrefixController
public class DashboardController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard-data")
    public JsonResponse getInfo() {
        IUserProjectCounts counts = userRepository.getUserProjectCounts(UserDetailsImpl.getCurrentUserId());
        return new JsonResponse(counts);
    }
}
