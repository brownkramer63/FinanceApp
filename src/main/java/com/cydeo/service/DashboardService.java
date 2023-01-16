package com.cydeo.service;


import com.cydeo.apiconsume.currencyApi.ExchangeRateDTO;

import java.math.BigDecimal;

public interface DashboardService {

    public ExchangeRateDTO getExchangeRates();
    BigDecimal totalProfitLoss();
}
