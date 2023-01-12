package com.cnsbd.jtrainpm.controller;

import com.cnsbd.jtrainpm.annotation.ApiPrefixController;
import com.cnsbd.jtrainpm.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiPrefixController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/reports/project-list")
    public void printProjectList() {
        reportService.printProjectList();
    }
}
