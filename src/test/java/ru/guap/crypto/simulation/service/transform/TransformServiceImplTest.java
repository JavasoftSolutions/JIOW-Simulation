package ru.guap.crypto.simulation.service.transform;

import org.apache.commons.lang3.RandomUtils;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import ru.guap.crypto.simulation.dto.Quote;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TransformServiceImplTest {

    private static List<Quote> data;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        data = generateData();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void transform() {
        TransformService transformService = new TransformServiceImpl();
        DataSetIterator dataSetIterator = transformService.transform(data);
    }

    private List<Quote> generateData() {
        return IntStream.range(0, 100_000).mapToObj(index ->
            Quote.builder().close(getRandom(5_000.00, 50_000.00)).volume(getRandom(5_000_000.00, 15_000_000)).build()
        ).collect(Collectors.toList());
    }

    private BigDecimal getRandom(double start, double end) {
        return BigDecimal.valueOf(RandomUtils.nextDouble(start, end));
    }

    private INDArray getBatchById(int i) {
        return null;
    }
}