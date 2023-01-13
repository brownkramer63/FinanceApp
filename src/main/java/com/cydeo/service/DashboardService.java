package com.cydeo.service;


import com.cydeo.apiconsume.currencyApi.ExchangeRateDTO;

public interface DashboardService {

    public ExchangeRateDTO getExchangeRates();
}
