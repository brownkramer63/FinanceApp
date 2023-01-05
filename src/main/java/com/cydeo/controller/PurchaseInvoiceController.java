package com.cydeo.controller;


import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.enums.InvoiceType;
import com.cydeo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;
    private final CompanyService companyService;

    public PurchaseInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ClientVendorService clientVendorService, ProductService productService, CompanyService companyService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
        this.companyService = companyService;
    }


    @GetMapping("/list")
    public String purchaseInvoiceList(Model model){
        model.addAttribute("invoices", invoiceService.listAllInvoicesByType(InvoiceType.PURCHASE) );
        return "invoice/purchase-invoice-list";
    }


    @GetMapping("/delete/{id}")
    public String deletePurchaseInvoice(@PathVariable("id") Long id){
        invoiceService.delete(id);
        return "redirect:/purchaseInvoices/list";
    }


    @GetMapping("/approve/{id}")
    public String approvePurchaseInvoice(@PathVariable("id") Long id){
        invoiceService.approve(id);
        return "redirect:/purchaseInvoices/list";

    }


    @GetMapping("/create")
    public String createPurchaseInvoice(Model model){

        model.addAttribute("newPurchaseInvoice", invoiceService.getNewPurchaseInvoice());
        model.addAttribute("vendors", clientVendorService.listAllClientVendors());



        return "/invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String savePurchaseInvoice( @ModelAttribute("newPurchaseInvoice") InvoiceDTO newPurchaseInvoice, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("vendors", clientVendorService.listAllClientVendors());
            return "invoice/purchase-invoice-create";
        }
        InvoiceDTO invoiceDTO = invoiceService.save(newPurchaseInvoice, InvoiceType.PURCHASE);

        model.addAttribute("invoice", invoiceDTO);
        model.addAttribute("vendors", clientVendorService.listAllClientVendors());
        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceProductId(invoiceDTO.getId()));
        model.addAttribute("products", productService.listAllProducts());



        return "invoice/purchase-invoice-update";


    }




    @GetMapping("/update/{id}")
    public String editPurchaseInvoice(@PathVariable("id") Long id, Model model){
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("vendors", clientVendorService.listAllClientVendors());
        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceProductId(id));
        model.addAttribute("products", productService.listAllProducts());


        return "/invoice/purchase-invoice-update";
    }


    @PostMapping("/update/{id}")
    public String updatePurchaseInvoice( @PathVariable("id") Long id, InvoiceDTO invoiceDTO){

        invoiceService.update(invoiceDTO, id);
        return "redirect:/purchaseInvoices/update/" + id;

    }

    @GetMapping("/removeInvoiceProduct/{id}/{invoiceProductId}")
    public String removeInvoiceProduct(@PathVariable("id") Long id, @PathVariable("invoiceProductId") Long invoiceProductId, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("invoice", invoiceService.findById(id));
        }
        invoiceProductService.removeInvoiceProduct( invoiceProductId);


        return "redirect:/purchaseInvoices/update/" + id;
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable("id") Long invoiceId, @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO, BindingResult bindingResult, Model model) throws Exception {
        if(bindingResult.hasErrors()){
            model.addAttribute("invoice", invoiceService.findById(invoiceId));
            model.addAttribute("vendors", clientVendorService.listAllClientVendors());
            model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceProductId(invoiceId));
            model.addAttribute("products", productService.listAllProducts());

            return "invoice/purchase-invoice-update";
        }
        invoiceProductService.addInvoiceProduct(invoiceId, invoiceProductDTO);
        return "redirect:/purchaseInvoices/update/" + invoiceId ;
    }


    @GetMapping("/print/{id}")
    public String printPurchaseInvoice(@PathVariable("id") Long id, Model model){



        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("company", companyService.getCompanyByLoggedInUser());
        return "/invoice/invoice_print";

    }

}
