package ru.guap.crypto.simulation.service.modeler;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import ru.guap.crypto.simulation.dto.Quote;

import java.util.List;

public interface RateModeler {

    void prepareModel(String quote, List<Quote> result);

    MultiLayerNetwork getPreparedModel(String quote);
}
