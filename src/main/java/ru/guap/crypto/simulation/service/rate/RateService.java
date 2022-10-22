package ru.guap.crypto.simulation.service.rate;

import ru.guap.crypto.simulation.dto.Quote;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface RateService {

    List<Quote> getRates(LocalDateTime from, LocalDateTime to, String interval) throws IOException;
}
