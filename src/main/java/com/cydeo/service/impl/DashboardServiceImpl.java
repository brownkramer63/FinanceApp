package com.cydeo.service.impl;


import com.cydeo.currencyApi.DashboardApiClient;
import com.cydeo.currencyApi.ExchangeRateDTO;
import com.cydeo.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardApiClient dashboardApiClient;

    public DashboardServiceImpl(DashboardApiClient dashboardApiClient) {
        this.dashboardApiClient = dashboardApiClient;
    }


    @Override
    public ExchangeRateDTO getExchangeRates() {

        var currencyValue = dashboardApiClient.getExchangeRates().getUsd();
        var date = dashboardApiClient.getExchangeRates().getDate();

        ExchangeRateDTO exchangeRateDTO = ExchangeRateDTO.builder()
                .date(date)
                .euro(currencyValue.getEur())
                .britishPound(currencyValue.getGbp())
                .canadianDollar(currencyValue.getCad())
                .japaneseYen(currencyValue.getJpy())
                .indianRupee(currencyValue.getInr()).build();

        return exchangeRateDTO;
    }
}
