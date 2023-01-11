package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.impl.InvoiceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;

    private final InvoiceRepository invoiceRepository;
    private final InvoiceServiceImpl invoiceService;
    private final MapperUtil mapperUtil;

    public ClientVendorController(ClientVendorService clientVendorService, InvoiceRepository invoiceRepository, InvoiceServiceImpl invoiceService, MapperUtil mapperUtil) {
        this.clientVendorService = clientVendorService;
        this.invoiceRepository = invoiceRepository;
        this.invoiceService = invoiceService;
        this.mapperUtil = mapperUtil;
    }

    @GetMapping("/list")
    public String clientVendorList(Model model){
        List<ClientVendorDTO> clientVendors= clientVendorService.listAllClientVendors();
        model.addAttribute("clientVendors",clientVendors);
        return "clientVendor/clientVendor-list";
  //should be good
    }
    @GetMapping("/create")
    public String createClientVendor(Model model){

        model.addAttribute("newClientVendor", new ClientVendorDTO());
        List<ClientVendorType> clientVendorTypes = Arrays.asList(ClientVendorType.values());
        log.info("size of clientVendorTypes" +clientVendorTypes.size());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));


        return "clientVendor/clientVendor-create";
    }
    @PostMapping("/create")
    public String insertClientVendor(@Valid @ModelAttribute("newClientVendor") ClientVendorDTO clientVendorDTO, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            List<ClientVendorType> clientVendorTypes = Arrays.asList(ClientVendorType.values());
            log.info("size of clientVendorTypes" +clientVendorTypes.size());
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
            return "/clientVendor/clientVendor-create";
        }
        clientVendorService.save(clientVendorDTO);
        return "redirect:/clientVendors/list";
    }

    @GetMapping("/update/{clientVendorId}")
    public String editClientVendor(@PathVariable("id") Long clientVendorId, Model model){

        model.addAttribute("clientVendor", clientVendorService.findById(clientVendorId));
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
    return "clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{clientVendorId}")
    public String editClientVendor( @Valid @ModelAttribute("clientVendor") ClientVendorDTO clientVendorDTO, @PathVariable("clientVendorId") Long clientVendorId, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("clientVendor", clientVendorDTO);
            model.addAttribute("clientVendor", clientVendorService.findById(clientVendorId));
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
            return "clientVendor/clientVendor-update";
        }
        clientVendorDTO.setId(clientVendorId);
        clientVendorService.update(clientVendorDTO);
        return "redirect:/clientVendors/list";

    }
    @GetMapping("/delete/{clientVendorId}")
    public String deleteClientVendorById(@PathVariable("clientVendorId") Long clientVendorId, RedirectAttributes redirectAttributes, Model model) throws IllegalAccessException {
        if (invoiceService.existsById(clientVendorId)){
            String error="cannot delete client/vendor linked to open invoice";
            redirectAttributes.addFlashAttribute("error", error);

            model.addAttribute("error",error);
;
            return "redirect:/clientVendors/list";
        }
        clientVendorService.delete(clientVendorId);
        return "redirect:/clientVendors/list";
    }




}
