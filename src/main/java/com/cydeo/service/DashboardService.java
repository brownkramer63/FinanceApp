package com.cydeo.service;

import com.cydeo.dto.CurrencyDTO;
import com.cydeo.dto.ExchangeRateDTO;

import java.util.List;

public interface DashboardService {

    public ExchangeRateDTO getExchangeRates();
}
