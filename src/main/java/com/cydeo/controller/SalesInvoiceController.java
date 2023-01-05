package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.enums.InvoiceType;
import com.cydeo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {


    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;
    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;

    public SalesInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, ProductService productService, InvoiceProductService invoiceProductService, CompanyService companyService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
        this.invoiceProductService = invoiceProductService;
        this.companyService = companyService;
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
    public String updatePurchaseInvoice( @PathVariable("id") Long id, @ModelAttribute("newPurchaseInvoice") InvoiceDTO newPurchaseInvoice, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("vendors", clientVendorService.listAllClientVendors());
            return "invoice/sales-invoice-create";
        }
        InvoiceDTO invoiceDTO1 = invoiceService.save(newPurchaseInvoice, InvoiceType.SALES);

        invoiceService.update(invoiceDTO1, id);
        return "redirect:/salesInvoices/list";

    }


    @GetMapping("/removeInvoiceProduct/{id}/{invoiceProductId}")
    public String removeInvoiceProduct(@PathVariable("id") Long id, @PathVariable("invoiceProductId") Long invoiceProductId){

        invoiceProductService.removeInvoiceProduct(invoiceProductId);
        log.info("product removed");
        return "redirect:/salesInvoices/update/" + id;
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable("id") Long id, @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO, BindingResult bindingResult, Model model) throws Exception {

        if(bindingResult.hasErrors()){
            model.addAttribute("invoice", invoiceService.findById(id));
            model.addAttribute("clients", clientVendorService.listAllClientVendors());
            model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
            model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceProductId(invoiceProductDTO.getId()));
            model.addAttribute("products", productService.listAllProducts());



            return "invoice/sales-invoice-update";
        }
        invoiceProductService.addInvoiceProduct(id, invoiceProductDTO);
        return "redirect:/salesInvoices/update/" +id ;
    }

    @GetMapping("/create")
    public String createSalesInvoice(Model model){

        model.addAttribute("newSalesInvoice", invoiceService.getNewSalesInvoice());
        model.addAttribute("clients", clientVendorService.listAllClientVendors());
        return "/invoice/sales-invoice-create";
    }


    @PostMapping("/create")
    public String saveSalesInvoice( @ModelAttribute("newSalesInvoice") InvoiceDTO newSalesInvoice, BindingResult bindingResult, Model model){

            if(bindingResult.hasErrors()){
                model.addAttribute("clients", clientVendorService.listAllClientVendors());
                return "invoice/sales-invoice-create";
            }

        InvoiceDTO invoiceDTO = invoiceService.save(newSalesInvoice, InvoiceType.SALES);

            log.info(" invoice getId" + invoiceDTO.getId());
        model.addAttribute("invoice", invoiceDTO);
        model.addAttribute("clients", clientVendorService.listAllClientVendors());
        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceProductId(invoiceDTO.getId()));
        model.addAttribute("products", productService.listAllProducts());



        return "redirect:/salesInvoices/update/" + invoiceDTO.getId().toString();

    }

    @GetMapping("/print/{id}")
    public String printSalesInvoice(@PathVariable("id") Long id, Model model){



        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("company", companyService.getCompanyByLoggedInUser());
        return "/invoice/invoice_print";

    }












}
