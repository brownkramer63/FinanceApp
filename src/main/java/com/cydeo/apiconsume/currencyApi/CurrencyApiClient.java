package com.cydeo.apiconsume.currencyApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json", name = "DASHBOARD-CLIENT")
public interface CurrencyApiClient {

    @GetMapping()
    CurrencyApiResponseDTO getExchangeRates();

}
