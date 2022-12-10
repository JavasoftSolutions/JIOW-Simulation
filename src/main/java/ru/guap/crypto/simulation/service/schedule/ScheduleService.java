package ru.guap.crypto.simulation.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.guap.crypto.simulation.service.rate.RateService;
import ru.guap.crypto.simulation.tool.Utility;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static ru.guap.crypto.simulation.tool.Utility.INTERVAL;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private static final LocalDateTime DATE_FROM = LocalDateTime.of(2018, 1, 1, 0,0);
    private static final Set<String> QUOTES = new HashSet<>();

    private final RateService rateService;

    @PostConstruct
    public void init() {
        QUOTES.add("EURS-USD");
        QUOTES.add("BTC-USD");
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void prepareModel() {
        for(String quote: QUOTES) {
            rateService.createModel(quote, DATE_FROM, LocalDateTime.now(), INTERVAL);
        }
    }

}
