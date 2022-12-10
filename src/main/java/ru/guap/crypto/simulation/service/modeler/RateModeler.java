package ru.guap.crypto.simulation.service.modeler;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import ru.guap.crypto.simulation.dto.QuoteDto;

import java.util.List;

public interface RateModeler {

    void prepareModel(String quote, List<QuoteDto> result);

    MultiLayerNetwork getPreparedModel(String quote);
}
