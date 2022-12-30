package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.ClientVendorService;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/clientVendor")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    private final MapperUtil mapperUtil;

    public ClientVendorController(ClientVendorService clientVendorService, MapperUtil mapperUtil) {
        this.clientVendorService = clientVendorService;
        this.mapperUtil = mapperUtil;
    }

    @GetMapping("/-list")
    public String clientVendorList(Model model){
        List<ClientVendorDTO> clientVendors= clientVendorService.listAllClientVendors();
        model.addAttribute("clientvendors",clientVendors);
        return "/clientVendor/-list";
  //check logic here
    }
    @PostMapping("/-create")
    public String createClientVendor(Model model){

        model.addAttribute("clientVendor", new ClientVendorDTO());
//        need to figure out the logic to add here


        return "/clientVendor/-create";
    }
    @PostMapping("/-create")
    public String insertClientVendor(@ModelAttribute("clientVendor") ClientVendorDTO clientVendorDTO, Model model){

        //check logic here
        clientVendorService.save(clientVendorDTO);
        return "redirect:/clientVendor/-create";
    }



}
