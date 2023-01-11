package com.cydeo.controller;

import com.cydeo.service.ReportingService;
import com.cydeo.service.impl.ReportingServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportingController {

private final ReportingService reportingService;
private final ReportingServiceImpl reportingServiceImpl;

    public ReportingController(ReportingService reportingService, ReportingServiceImpl reportingServiceImpl) {
        this.reportingService = reportingService;
        this.reportingServiceImpl = reportingServiceImpl;
    }

    @GetMapping("/stockData")
    public String listAllValidStockData(Model model){

        model.addAttribute("invoiceProducts", reportingService.getStockReport());

        return "report/stock-report";
    }

    @GetMapping("profitLossData")
    public String MonthlyProfitOrLossReport(Model model){
    model.addAttribute("monthlyProfitLossDataMap",reportingServiceImpl.profitLossDataMap());


        return "report/profit-loss-report";
     }
}
