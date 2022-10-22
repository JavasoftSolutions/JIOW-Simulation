package ru.guap.crypto.simulation.service.rate;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.guap.crypto.simulation.dto.Quote;
import ru.guap.crypto.simulation.mapper.QuoteMapper;
import ru.guap.crypto.simulation.service.client.RestClient;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service("BTC-USD")
public class BtcUsdRateServiceImpl implements RateService {

    private static final String QUOTE = "BTC-USD";
    private final QuoteMapper mapper;
    private final RestClient restClient;

    @Override
    public List<Quote> getRates(LocalDateTime from, LocalDateTime to, String interval) throws IOException {
        String stringWithQuotes = restClient.getQuoteString(QUOTE, from, to, interval);
        CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new StringReader(stringWithQuotes));
        List<Quote> result = new ArrayList<>();
        for (CSVRecord record : parser) {
            result.add(mapper.map(record));
        }
        return result;
    }
}
