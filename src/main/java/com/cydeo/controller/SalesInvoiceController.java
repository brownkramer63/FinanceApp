package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {


    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;

    public SalesInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, ProductService productService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String listAllInvoice(Model model){
        model.addAttribute("invoices", invoiceService.listAllInvoice());
        return "/invoice/sales-invoice-list";
    }


    @GetMapping("/create")
    public String createInvoice(Model model){
        model.addAttribute("newSalesInvoice", new InvoiceDTO());
        model.addAttribute("clients", clientVendorService.findAll());
        return "/invoice/sales-invoice-create";
    }



    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long id){
        invoiceService.delete(id);
        return "redirect:/invoice/sales-invoice-list";
    }



}
