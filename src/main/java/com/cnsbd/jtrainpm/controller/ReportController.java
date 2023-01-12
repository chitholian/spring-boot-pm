package com.cnsbd.jtrainpm.controller;

import com.cnsbd.jtrainpm.annotation.ApiPrefixController;
import com.cnsbd.jtrainpm.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiPrefixController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/reports/project-list")
    public ResponseEntity<byte[]> printProjectList() {
        byte[] data = reportService.printProjectList();
        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"Project-List.pdf\"")
                .body(data);
    }
}
