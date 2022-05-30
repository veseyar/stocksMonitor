package com.veseyar.monitor.client.reports;

import com.veseyar.monitor.client.ContextProvider;
import com.veseyar.monitor.client.config.ApiConnector;
import com.veseyar.monitor.client.tools.IoTools;
import ru.tinkoff.piapi.contract.v1.LastPrice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

//Класс, формурующий свечи согласно figi в \\OLAP\\figi
public class AllCandlesReport {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy hh:mm");;

    public void createCandleReport(ApiConnector apiConnector, int days, String source, String target) throws Exception {
        //Создаем класс с помощью которого будет получать информацию по акциям
        ContextProvider contextProvider = new ContextProvider(apiConnector);

        //Указываем путь откуда будет происходить чтение
        BufferedReader reader = new BufferedReader(new FileReader(source));

        //Указываем путь куда будет происходить запись
        Files.deleteIfExists(Paths.get(target));
        IoTools.createFileIfNotExists(target);
        FileWriter writer = new FileWriter(target);
        List<String> figis = reader.lines().collect(Collectors.toList());

        List<LastPrice> lastPrices = apiConnector.getApi().getMarketDataService().getLastPricesSync(figis);
        for (LastPrice price : lastPrices) {
            writer.write(price.getFigi());
            writer.write(";");
            writer.write(OffsetDateTime.ofInstant(Instant.ofEpochSecond(price.getTime().getSeconds()), ZoneId.of("UTC")).format(formatter));
            writer.write(";");
            writer.write(price.getPrice().getUnits() + "");
            writer.write(".");
            writer.write(price.getPrice().getNano() + "\n");
        }

        writer.close();
    }

}
