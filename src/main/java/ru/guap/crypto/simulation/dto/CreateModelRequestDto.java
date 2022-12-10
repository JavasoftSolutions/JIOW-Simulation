package ru.guap.crypto.simulation.dto;

import lombok.Data;

@Data
public class CreateModelRequestDto {
    private String quote;
    private String dateFrom;
    private String dateTo;
    private String interval;
}
