package com.veseyar.monitor.client.reports;

import ru.tinkoff.piapi.contract.v1.Share;

import java.io.IOException;
import java.util.List;

public class AllFigiStockReportForm extends ReportForm<Share> {

    public AllFigiStockReportForm(List<Share> instruments) throws IOException {
        super();
        setFileName("allFigis.csv");
        setReportObjects(instruments);
        setFields(new String[]{"figi"});
    }

}