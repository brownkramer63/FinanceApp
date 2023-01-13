package com.cydeo.service.impl;


import com.cydeo.apiconsume.currencyApi.CurrencyApiClient;
import com.cydeo.apiconsume.currencyApi.ExchangeRateDTO;
import com.cydeo.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final CurrencyApiClient currencyApiClient;

    public DashboardServiceImpl(CurrencyApiClient currencyApiClient) {
        this.currencyApiClient = currencyApiClient;
    }


    @Override
    public ExchangeRateDTO getExchangeRates() {

        var apiResponse = currencyApiClient.getExchangeRates();
        var currencyApiResponseForUsd = apiResponse.getUsd();
        var date = apiResponse.getDate();

        ExchangeRateDTO exchangeRateDTO = ExchangeRateDTO.builder()
                .date(date)
                .euro(currencyApiResponseForUsd.getEur())
                .britishPound(currencyApiResponseForUsd.getGbp())
                .canadianDollar(currencyApiResponseForUsd.getCad())
                .japaneseYen(currencyApiResponseForUsd.getJpy())
                .indianRupee(currencyApiResponseForUsd.getInr()).build();
        log.info("Currencies are Retrieved for date : " + date);
        return exchangeRateDTO;
    }
}
