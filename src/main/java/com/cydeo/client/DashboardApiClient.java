package com.cydeo.client;

import com.cydeo.dto.CurrencyDTO;
import com.cydeo.dto.UsdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json", name = "DASHBOARD-CLIENT")
public interface DashboardApiClient {

    @GetMapping()
    CurrencyDTO getExchangeRates();

}
