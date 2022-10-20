package ru.guap.crypto.simulation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.guap.crypto.simulation.dto.Quote;
import ru.guap.crypto.simulation.mapper.QuoteMapper;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class Simulation {

    @Autowired
    private QuoteMapper mapper;

    @GetMapping
    public List<Quote> getRates() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String quoteString = restTemplate.getForObject("https://query1.finance.yahoo.com/v7/finance/download/BTC-USD?period1=1633678351&period2=1665214351&interval=1d&events=history", String.class);
        CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new StringReader(quoteString));
        List<Quote> result = new ArrayList<>();
        for(CSVRecord record: parser) {
            result.add(mapper.map(record));
        }
        return result;
    }

    public static void main(String[] args) {
        SpringApplication.run(Simulation.class, args);
    }
}
