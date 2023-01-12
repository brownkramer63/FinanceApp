package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.service.DashboardService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping()

public class DashboardController {
    private final InvoiceService invoiceService;
    private final DashboardService dashboardService;

    public DashboardController(InvoiceService invoiceService, DashboardService dashboardService) {
        this.invoiceService = invoiceService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String navigateToDashboard(Model model) {

        Map<String, BigDecimal> map = new TreeMap<>();
        map.put("totalCost",invoiceService.totalCostOfApprovedInvoices());
        map.put("totalSales", invoiceService.totalSalesOfApprovedInvoices());
        map.put("profitLoss", invoiceService.totalCostOfApprovedInvoices());
        List<InvoiceDTO> collect = invoiceService.listAllInvoice()

                .stream().limit(3).collect(Collectors.toList());

        model.addAttribute("summaryNumbers", map);
        model.addAttribute("invoices", collect);

        model.addAttribute("exchangeRates", dashboardService.getExchangeRates());

        return "dashboard";
    }


}

