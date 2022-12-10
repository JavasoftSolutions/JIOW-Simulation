package ru.guap.crypto.simulation.service.transform;

import org.apache.commons.lang3.RandomUtils;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import ru.guap.crypto.simulation.dto.QuoteDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class TransformServiceImplTest {

    private static List<QuoteDto> data;

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

    private List<QuoteDto> generateData() {
        return IntStream.range(0, 100_000).mapToObj(index ->
            QuoteDto.builder().close(getRandom(5_000.00, 50_000.00)).volume(getRandom(5_000_000.00, 15_000_000)).build()
        ).collect(Collectors.toList());
    }

    private BigDecimal getRandom(double start, double end) {
        return BigDecimal.valueOf(RandomUtils.nextDouble(start, end));
    }

    private INDArray getBatchById(int i) {
        return null;
    }
}