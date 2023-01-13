package com.cydeo.service;

import com.cydeo.dto.AccessTokenDTO;
import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.CountryListDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressService {

    List<String> getListOfCountries();



}
