package ru.guap.crypto.simulation.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
public class YahooFinanceRestClientImpl implements RestClient {

    private static final ZoneId NY_TIMEZONE = ZoneId.of("America/New_York");
    private final RestTemplate restTemplate;

    @Override
    public String getQuoteString(String quoteString, LocalDateTime from, LocalDateTime to, String interval) {
        ZoneOffset zoneOffset = getZoneOffset();
        String queryString = String.format("https://query1.finance.yahoo.com/v7/finance/download/%s?period1=%d&period2=%d&interval=%s&events=history", quoteString, from.toEpochSecond(zoneOffset), to.toEpochSecond(zoneOffset), interval);
        return restTemplate.getForObject(queryString, String.class);
    }

    private ZoneOffset getZoneOffset() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.systemDefault());
        return localDateTime.atZone(NY_TIMEZONE).getOffset();
    }
}
