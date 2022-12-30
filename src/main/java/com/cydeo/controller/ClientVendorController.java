package com.cydeo.controller;

import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientVendor")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    private final MapperUtil mapperUtil;

    public ClientVendorController(ClientVendorService clientVendorService, MapperUtil mapperUtil) {
        this.clientVendorService = clientVendorService;
        this.mapperUtil = mapperUtil;
    }

}
