package com.cydeo.service.impl;

import com.cydeo.client.ConsumeCountriesAPI;
import com.cydeo.dto.AccessTokenDTO;
import com.cydeo.dto.AddressDTO;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ConsumeCountriesAPI consumeCountriesAPI;



    public AddressServiceImpl(AddressRepository addressRepository, ConsumeCountriesAPI consumeCountriesAPI) {
        this.addressRepository = addressRepository;
        this.consumeCountriesAPI = consumeCountriesAPI;
    }


    @Override
    public List<String> getListOfCountries() {
       AccessTokenDTO accessToken= consumeCountriesAPI.getAccessToken();
        return consumeCountriesAPI.getListOfCountries();
    }

    @Override
    public AccessTokenDTO getAccessToken() {
        return consumeCountriesAPI.getAccessToken();
    }
}
