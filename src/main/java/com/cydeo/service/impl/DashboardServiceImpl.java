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

        ExchangeRateDTO exchangeRateDTO = ExchangeRateDTO.builder()
                .euro(dashboardApiClient.getExchangeRates().getUsd().getEur())
                .britishPound(dashboardApiClient.getExchangeRates().getUsd().getGbp())
                .canadianDollar(dashboardApiClient.getExchangeRates().getUsd().getCad())
                .japaneseYen(dashboardApiClient.getExchangeRates().getUsd().getJpy())
                .indianRupee(dashboardApiClient.getExchangeRates().getUsd().getInr()).build();

        return exchangeRateDTO;
    }
}
