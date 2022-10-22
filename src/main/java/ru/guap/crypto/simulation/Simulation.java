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
public class Simulation {



    public static void main(String[] args) {
        SpringApplication.run(Simulation.class, args);
    }
}
