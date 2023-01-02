package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.ClientVendorService;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
        model.addAttribute("clientvendors",clientVendors);
        return "redirect:/clientVendors/list";
  //should be good
    }
    @PostMapping("/create")
    public String createClientVendor(Model model){

        model.addAttribute("clientVendor", new ClientVendorDTO());
        model.addAttribute("country list", List.of("USA","Canada","Germany") );
        model.addAttribute("clientVendorTypes", ClientVendorType.values());


        return "redirect:/clientVendors/create";
    }
    @PostMapping("/create")
    public String insertClientVendor(@ModelAttribute("clientVendor") ClientVendorDTO clientVendorDTO, Model model){

        clientVendorService.save(clientVendorDTO);
        return "redirect:/clientVendors/create";
    }

    @GetMapping("/update/{id}")
    public String editClientVendor(@PathVariable("clientVendorId") Long clientVendorId, Model model){

        model.addAttribute("clientVendor", clientVendorService.findById(clientVendorId));
        model.addAttribute("country list", List.of("USA","Canada","Germany") );
        model.addAttribute("clientVendorTypes", ClientVendorType.values());
    return "clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{id}")
    public String editClientVendor(@ModelAttribute("clientVendor") ClientVendorDTO clientVendorDTO, @PathVariable("clientVendorId") Long clientVendorId,Model model){

        clientVendorService.save(clientVendorDTO);
        return "redirect:/clientVendors/list";

    }




}
