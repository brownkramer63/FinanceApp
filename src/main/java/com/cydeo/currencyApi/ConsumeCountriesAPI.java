package com.cydeo.client;

import com.cydeo.dto.AccessTokenDTO;
import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.ClientVendorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "https://www.universal-tutorial.com/api", name = "Countries-List")
public interface ConsumeCountriesAPI {

@GetMapping("/getaccesstoken")
AccessTokenDTO getAccessToken();// will return bearer token

    @GetMapping("/countries/")
    List<String> getListOfCountries();

    //need to add in a method to get token

}
