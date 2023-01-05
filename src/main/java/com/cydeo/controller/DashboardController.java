package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping()

public class DashboardController {
    private final InvoiceService invoiceService;

    public DashboardController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/dashboard")
    public String navigateToDashboard(Model model){
        Map<String, Integer> map = new TreeMap<>();
        map.put("totalCost", 7800);
        map.put("totalSales", 800);
        map.put("profitLoss", 2000);
        List<InvoiceDTO> collect = invoiceService.listAllInvoice()
                .stream().limit(3).collect(Collectors.toList());

        model.addAttribute("summaryNumbers", map);
        model.addAttribute("invoices", collect);


        return "dashboard";
    }

}

