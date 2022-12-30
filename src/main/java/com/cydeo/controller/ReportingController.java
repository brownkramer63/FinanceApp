package com.cydeo.controller;

import com.cydeo.service.ReportingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class ReportingController {

private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/stock-report")
    public String listAllValidStockData(Model model){

        //need logic here

        return "/stock-report";
    }
}
