package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.ClientVendorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    private final MapperUtil mapperUtil;

    public ClientVendorController(ClientVendorService clientVendorService, MapperUtil mapperUtil) {
        this.clientVendorService = clientVendorService;
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
//        model.addAttribute("country", List.of("USA","Canada","Germany") );
        List<ClientVendorType> clientVendorTypes = Arrays.asList(ClientVendorType.values());
        log.info("size of clientVendorTypes" +clientVendorTypes.size());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));


        return "clientVendor/clientVendor-create";
    }
    @PostMapping("/create")
    public String insertClientVendor(@ModelAttribute("newClientVendor") ClientVendorDTO clientVendorDTO, Model model){

        clientVendorService.save(clientVendorDTO);
        return "redirect:/clientVendors/list";
    }

    @GetMapping("/update/{clientVendorId}")
    public String editClientVendor(@PathVariable("clientVendorId") Long clientVendorId, Model model){

        model.addAttribute("clientVendor", clientVendorService.findById(clientVendorId));
        model.addAttribute("country", List.of("USA","Canada","Germany") );
        model.addAttribute("clientVendorType", ClientVendorType.values());
    return "clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{id}")
    public String editClientVendor(@ModelAttribute("clientVendor") ClientVendorDTO clientVendorDTO, @PathVariable("clientVendorId") Long clientVendorId,Model model){

        clientVendorService.save(clientVendorDTO);
        return "redirect:/clientVendors/list";

    }




}
