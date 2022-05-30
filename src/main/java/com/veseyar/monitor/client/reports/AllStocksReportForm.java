package com.veseyar.monitor.client.reports;

import ru.tinkoff.piapi.contract.v1.Share;

import java.io.IOException;
import java.util.List;

public class AllStocksReportForm extends ReportForm<Share> {

    public AllStocksReportForm(List<Share> instruments) throws IOException {
        super();

        setFileName("allStocks.csv");
        setReportObjects(instruments);
        setReportName("Акции");
        setFields(new String[]{"figi", "name", "ticker", "currency"});
        setHeaders(new String[]{"figi", "наименование", "тикер", "валюта"});
    }

}
