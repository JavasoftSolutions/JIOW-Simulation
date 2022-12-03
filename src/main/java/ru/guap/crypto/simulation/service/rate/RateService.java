package ru.guap.crypto.simulation.service.rate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RateService {

    String getRates(String quote, BigDecimal predCloseRate, BigDecimal predCloseVolume) throws IOException;

    String createModel(String quote, LocalDateTime from, LocalDateTime to, String interval);

}
