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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;

    public PurchaseInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ClientVendorService clientVendorService, ProductService productService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
    }


    @GetMapping("/list")
    public String purchaseInvoiceList(Model model){
        model.addAttribute("invoices", invoiceService.listAllInvoicesByType(InvoiceType.PURCHASE) );
        return "/invoice/purchase-invoice-list";
    }


    @GetMapping("/delete/{id}")
    public String deletePurchaseInvoice(@PathVariable("id") Long id){
        invoiceService.delete(id);
        return "redirect:/purchaseInvoices/list";
    }


    @GetMapping("/approve/{id}")
    public String approvePurchaseInvoice(@PathVariable("id") Long id){
        invoiceService.approve(id);
        return "redirect:/purchaseInvoicesList";

    }




    @GetMapping("/create")
    public String createPurchaseInvoice(Model model){

        model.addAttribute("newPurchaseInvoice", invoiceService.getNewPurchaseInvoice());
        model.addAttribute("vendors", clientVendorService.findAllByClientVendorName());


        return "/invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String savePurchaseInvoice(@ModelAttribute("newPurchaseInvoice") InvoiceDTO newPurchaseInvoice, Model model){

        model.addAttribute("newPurchaseInvoice", invoiceService.create(newPurchaseInvoice, InvoiceType.PURCHASE));
        model.addAttribute("vendors", clientVendorService.findAllByClientVendorName());
        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("products", productService.listAllProducts());
        model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceProductId(newPurchaseInvoice.getId()));

        return "/invoice/purchase-invoice-update";

    }



    @GetMapping("/update/{id}")
    public String editPurchaseInvoice(@PathVariable("id") Long id, Model model){
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("vendors", clientVendorService.findAll());
        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceProductId(id));
        model.addAttribute("products", productService.listAllProducts());


        return "/invoice/purchase-invoice-update";
    }


    @PostMapping("/update/{id}")
    public String updatePurchaseInvoice(@ModelAttribute("invoice") InvoiceDTO invoiceDTO, @PathVariable("id") Long id){

        invoiceProductService.update(invoiceDTO, id);
        return "redirect: purchaseInvoices/list";

    }

    @GetMapping("/removeInvoiceProduct/{id}")
    public String removeInvoiceProduct(@PathVariable("id") Long id){
        invoiceProductService.removeInvoiceProduct(id);
        return "redirect:purchaseInvoices/update";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable("id") Long id, @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO){
        invoiceProductService.addInvoiceProduct(invoiceProductDTO);
        return "redirect:/purchaseInvoices/update";
    }

}
