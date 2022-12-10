package ru.guap.crypto.simulation.mapper;


import org.apache.commons.csv.CSVRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.guap.crypto.simulation.dto.QuoteDto;
import ru.guap.crypto.simulation.tool.Utility;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuoteMapper {

    default QuoteDto map(CSVRecord record) {
        return QuoteDto.builder().date(LocalDate.from(Utility.FORMATTER.parse(record.get("Date"))))
                .close(new BigDecimal(record.get("Close")))
                .open(new BigDecimal(record.get("Open")))
                .high(new BigDecimal(record.get("High")))
                .low(new BigDecimal(record.get("Low")))
                .volume(new BigDecimal(record.get("Volume")))
                .adjClose(new BigDecimal(record.get("Adj Close"))).build();
    }

}
