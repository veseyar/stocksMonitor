package com.veseyar.monitor.client.reports;

import ru.tinkoff.piapi.contract.v1.Share;

import java.io.IOException;
import java.util.List;

public class AllNameStockReportForm extends ReportForm<Share> {

    public AllNameStockReportForm(List<Share> instruments) throws IOException {
        super();
        setFileName("allNames.csv");
        setReportObjects(instruments);
        setFields(new String[]{"name"});
    }

}