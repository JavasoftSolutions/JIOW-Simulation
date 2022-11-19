package ru.guap.crypto.simulation.service.rate;

import java.io.IOException;
import java.time.LocalDateTime;

public interface RateService {

    String getRates(LocalDateTime from, LocalDateTime to, String interval) throws IOException;
}
