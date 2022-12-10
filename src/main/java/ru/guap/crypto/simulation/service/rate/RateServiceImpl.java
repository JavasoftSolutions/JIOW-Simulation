package ru.guap.crypto.simulation.service.rate;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Service;
import ru.guap.crypto.simulation.dto.QuoteDto;
import ru.guap.crypto.simulation.mapper.QuoteMapper;
import ru.guap.crypto.simulation.service.client.RestClient;
import ru.guap.crypto.simulation.service.modeler.RateModeler;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RateServiceImpl implements RateService {

    private final QuoteMapper mapper;
    private final RestClient restClient;
    private final RateModeler rateModeler;

    @Override
    public String getRates(String quote, BigDecimal predCloseRate, BigDecimal predCloseVolume) throws IOException {
        MultiLayerNetwork net = rateModeler.getPreparedModel(quote);

        final INDArray input = Nd4j.create(new double[]{predCloseRate.doubleValue(), predCloseVolume.doubleValue()}, 1, 2);
        INDArray out = net.output(input, false);

        return out.toStringFull();
    }

    @Override
    public String createModel(String quote, LocalDateTime from, LocalDateTime to, String interval) {
        String stringWithQuotes = restClient.getQuoteString(quote, from, to, interval);

        CSVParser parser = null;
        try {
            parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new StringReader(stringWithQuotes));
        } catch (IOException e) {
            throw new RuntimeException("Unable to parse retrieved quotes: " + stringWithQuotes);
        }
        List<QuoteDto> result = new ArrayList<>();
        for (CSVRecord record : parser) {
            result.add(mapper.map(record));
        }
        rateModeler.prepareModel(quote, result);
        return "OK";
    }

}
