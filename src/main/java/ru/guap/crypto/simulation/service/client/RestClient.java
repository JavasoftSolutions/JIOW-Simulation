package ru.guap.crypto.simulation.service.client;

import java.time.LocalDateTime;

public interface RestClient {

    String getQuoteString(String quoteString, LocalDateTime from, LocalDateTime to, String interval);

}
