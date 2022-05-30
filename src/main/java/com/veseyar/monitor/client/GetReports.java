package com.veseyar.monitor.client;

import com.veseyar.monitor.client.config.ApiConnector;
import com.veseyar.monitor.client.config.Parameters;
import com.veseyar.monitor.client.reports.*;
import com.veseyar.monitor.client.reports.*;
import io.github.cdimascio.dotenv.Dotenv;
import ru.tinkoff.piapi.contract.v1.Share;

import java.util.ArrayList;
import java.util.List;

public class GetReports {

    public static void main(String[] args) throws Exception {
        Dotenv env = Dotenv.load();

        //Создаем подключение используя токен в режиме sandBox
        String token = env.get("TOKEN");
        boolean isSandboxMode = Boolean.parseBoolean(env.get("SANDBOX"));
        Parameters parameters = new Parameters(token, isSandboxMode);
        //Создаем апиконнектор который будет использоваться для подключения с заданными параметрами
        ApiConnector apiConnector = new ApiConnector(parameters);
        //Создаем класс с помощью которого будет получать информацию по акциям
        ContextProvider contextProvider = new ContextProvider(apiConnector);

        //Коллекции для списка акций, имен и figi
        List<ReportForm> reportStocks = new ArrayList<>();
        List<ReportForm> reportNames = new ArrayList<>();
        List<ReportForm> reportFigis = new ArrayList<>();

        //Получение отчетов по акциям, имени и figi, и их экспорт в файл
        List<Share> stocks = contextProvider.getStocks();
        reportStocks.add(new AllStocksReportForm(stocks));
        reportStocks.forEach(ReportForm<Share>::doCommonExport);

        reportNames.add(new AllNameStockReportForm(stocks));
        reportNames.forEach(ReportForm<Share>::doSingleExport);

        reportFigis.add(new AllFigiStockReportForm(stocks));
        reportFigis.forEach(ReportForm<Share>::doSingleExport);

        //Формируем отчет по всем свечам
        AllCandlesReport candleReport = new AllCandlesReport();
        String allPrices = "OLAP/allPrices.csv";
        String allFigis = "OLAP/allFigis.csv";
        candleReport.createCandleReport(apiConnector, 4, allFigis, allPrices);

        System.out.println("Формируем отчет из полученных данных...");

        //Формируем финальный отчет из собранных данных
        FinalReport finalReport = new FinalReport();
        String allStocks = "OLAP/allStocks.csv";
        String report = "report.csv";
        finalReport.doReportFromCubes(allPrices, allStocks, report);
    }
}