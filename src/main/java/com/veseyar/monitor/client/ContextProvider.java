package com.veseyar.monitor.client;

import com.veseyar.monitor.client.config.ApiConnector;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.Etf;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.List;


/*
Класс получения информации
Получаем инструменты из MarketInstrumentList: акции, фьючерсы, валюты
 */
public class ContextProvider {

    private final ApiConnector apiConnector;

    public ContextProvider(ApiConnector apiConnector) {
        this.apiConnector = apiConnector;
    }

    public List<Share> getStocks() {
        return getApi().getInstrumentsService().getAllSharesSync();
    }

    public List<Share> getBonds() {
        return getApi().getInstrumentsService().getAllSharesSync();
    }

    public List<Etf> getEtfs() {
        return getApi().getInstrumentsService().getAllEtfsSync();
    }

    public List<Currency> getCurrencies() throws Exception {
        return getApi().getInstrumentsService().getAllCurrenciesSync();
    }

    public List<LastPrice> getLastPrices(Iterable<String> figis) {
        return apiConnector.getApi().getMarketDataService().getLastPrices(figis).join();
    }

    private InvestApi getApi() {
        return apiConnector.getApi();
    }

}