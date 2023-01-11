package com.cydeo.service.impl;


import com.cydeo.client.DashboardApiClient;
import com.cydeo.dto.CurrencyDTO;
import com.cydeo.dto.ExchangeRateDTO;
import com.cydeo.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardApiClient dashboardApiClient;

    public DashboardServiceImpl(DashboardApiClient dashboardApiClient) {
        this.dashboardApiClient = dashboardApiClient;
    }


    @Override
    public ExchangeRateDTO getExchangeRates() {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setEuro(dashboardApiClient.getExchangeRates().getUsd().getEur());
        exchangeRateDTO.setBritishPound(dashboardApiClient.getExchangeRates().getUsd().getGbp());
        exchangeRateDTO.setCanadianDollar(dashboardApiClient.getExchangeRates().getUsd().getCad());
        exchangeRateDTO.setJapaneseYen(dashboardApiClient.getExchangeRates().getUsd().getJpy());
        exchangeRateDTO.setIndianRupee(dashboardApiClient.getExchangeRates().getUsd().getInr());
        return exchangeRateDTO;
    }
}
