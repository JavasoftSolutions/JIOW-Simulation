package ru.guap.crypto.simulation.mapper;


import org.apache.commons.csv.CSVRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.guap.crypto.simulation.dto.Quote;
import ru.guap.crypto.simulation.tool.Utility;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuoteMapper {

    default Quote map(CSVRecord record) {
        Quote result = new Quote();
        result.setDate(LocalDate.from(Utility.FORMATTER.parse(record.get("Date"))));
        result.setClose(new BigDecimal(record.get("Close")));
        result.setOpen(new BigDecimal(record.get("Open")));
        result.setHigh(new BigDecimal(record.get("High")));
        result.setLow(new BigDecimal(record.get("Low")));
        result.setVolume(new BigDecimal(record.get("Volume")));
        result.setAdjClose(new BigDecimal(record.get("Adj Close")));
        return result;
    }

}
