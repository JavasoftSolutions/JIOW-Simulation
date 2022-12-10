package ru.guap.crypto.simulation.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.guap.crypto.simulation.dto.CreateModelRequestDto;
import ru.guap.crypto.simulation.service.rate.RateService;
import ru.guap.crypto.simulation.tool.Utility;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static ru.guap.crypto.simulation.tool.Utility.*;

@RequiredArgsConstructor
@RestController
public class RateController {

    private final RateService rateService;

    @GetMapping
    public String getRates(String quote, BigDecimal closeRate, BigDecimal closeVolume) throws IOException {
        if (StringUtils.isEmpty(quote) ||  null == closeRate || null == closeVolume) {
            throw new RuntimeException(EXCEPTION_PLEASE_CHECK_THAT_QUOTE_AND_CLOSE_RATE_AND_CLOSE_VOLUME_WERE_PROVIDED);
        }
        return rateService.getRates(quote, closeRate, closeVolume);
    }

    @PostMapping
    public String createModel(@RequestBody CreateModelRequestDto createModelRequest) {
        if (StringUtils.isEmpty(createModelRequest.getQuote()) || StringUtils.isEmpty(createModelRequest.getDateFrom()) || StringUtils.isEmpty(createModelRequest.getDateTo()) || StringUtils.isEmpty(createModelRequest.getInterval())) {
            throw new RuntimeException(EXCEPTION_PLEASE_CHECK_THAT_QUOTE_AND_DATE_FROM_AND_DATE_TO_AND_INTERVAL_WERE_PROVIDED);
        }
        LocalDateTime from = LocalDate.from(Utility.FORMATTER.parse(createModelRequest.getDateFrom())).atStartOfDay();
        LocalDateTime to = LocalDate.from(Utility.FORMATTER.parse(createModelRequest.getDateTo())).plusDays(1).atStartOfDay();
        return rateService.createModel(createModelRequest.getQuote(), from, to, INTERVAL);
    }
}
