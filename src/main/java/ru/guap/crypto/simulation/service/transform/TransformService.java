package ru.guap.crypto.simulation.service.transform;

import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import ru.guap.crypto.simulation.dto.Quote;

import java.util.List;

public interface TransformService {

    DataSetIterator transform(List<Quote> queueList);
}
