package ru.guap.crypto.simulation.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.guap.crypto.simulation.dto.Quote;
import ru.guap.crypto.simulation.service.rate.RateService;
import ru.guap.crypto.simulation.tool.Utility;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RateController {

    private final ApplicationContext context;

    @GetMapping
    public List<Quote> getRates(String quote, String dateFrom, String dateTo, String interval) throws IOException {
        if (StringUtils.isEmpty(quote) || StringUtils.isEmpty(dateFrom) || StringUtils.isEmpty(dateTo) || StringUtils.isEmpty(interval)) {
            throw new RuntimeException("Please check that \"quote\" and \"dateFrom\" and \"dateTo\" and \"interval\" were provided");
        }
        RateService rateService = (RateService) context.getBean(quote);
        LocalDateTime  from = LocalDate.from(Utility.FORMATTER.parse(dateFrom)).atStartOfDay();
        LocalDateTime  to = LocalDate.from(Utility.FORMATTER.parse(dateTo)).plusDays(1).atStartOfDay();
        return rateService.getRates(from, to, "1d");
    }

}
