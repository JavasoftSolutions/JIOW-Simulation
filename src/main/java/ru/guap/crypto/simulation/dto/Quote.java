package ru.guap.crypto.simulation.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Quote {
    private LocalDate date;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal volume;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal adjClose;
}
