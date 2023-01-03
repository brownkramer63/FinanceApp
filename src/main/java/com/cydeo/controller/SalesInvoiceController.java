package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.enums.InvoiceType;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {


    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;
    private final InvoiceProductService invoiceProductService;

    public SalesInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, ProductService productService, InvoiceProductService invoiceProductService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
        this.invoiceProductService = invoiceProductService;
    }

    @GetMapping("/list")
    public String listAllInvoice(Model model){
        model.addAttribute("invoices", invoiceService.listAllInvoicesByType(InvoiceType.SALES));
        return "/invoice/sales-invoice-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSalesInvoice(@PathVariable("id") Long id){
        invoiceService.delete(id);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/approve/{id}")
    public String approveSalesInvoice(@PathVariable("id") Long id){
        invoiceService.approve(id);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/update/{id}")
    public String editSalesInvoice(@PathVariable("id") Long id, Model model){
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("clients", clientVendorService.listAllClientVendors());
        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("products", productService.listAllProducts());
        model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceProductId(id));

        return"/invoice/sales-invoice-update";
    }

    @PostMapping("/update/{id}")
    public String updateSalesInvoice(@PathVariable("id") Long id, @ModelAttribute("invoice")InvoiceDTO invoiceDTO){
        invoiceProductService.update(invoiceDTO, id);
        return "redirect:/salesInvoices/update";


    }

    @GetMapping("/removeInvoiceProduct/{id}")
    public String removeInvoiceProduct(@PathVariable("id") Long id){
        invoiceProductService.removeInvoiceProduct(id);
        return "redirect:salesInvoices/update";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable("id") Long id, @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO){
        invoiceProductService.save(id, invoiceProductDTO);
        return "redirect:/salesInvoices/update";
    }

    @GetMapping("/create")
    public String createSalesInvoice(Model model){

        model.addAttribute("newSalesInvoice", invoiceService.getNewSalesInvoice());
        model.addAttribute("vendors", clientVendorService.listAllClientVendors());
        return "/invoice/sales-invoice-create";
    }


    @PostMapping("/create")
    public String saveSalesInvoice( @ModelAttribute("newSalesInvoice") InvoiceDTO newSalesInvoice, BindingResult bindingResult, Model model){

            if(bindingResult.hasErrors()){
                model.addAttribute("newSalesInvoice", invoiceService.getNewSalesInvoice());
                model.addAttribute("vendors", clientVendorService.listAllClientVendors());
                return "/invoice/sales-invoice-update";
            }
            invoiceService.save(newSalesInvoice, InvoiceType.PURCHASE);

            return "redirect:/salesInvoices/create";


    }

    @GetMapping("/print/{id}")
    public String printSalesInvoice(@PathVariable("id") Long id, Model model){

        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceProductId(id));

        return "/invoice/invoice_print";

    }












}
