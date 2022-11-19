package ru.guap.crypto.simulation.service.transform;

import org.deeplearning4j.datasets.iterator.utilty.ListDataSetIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Service;
import ru.guap.crypto.simulation.dto.Quote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class TransformServiceImpl implements TransformService {

    private static int SAMPLE_WINDOW = 30;

    @Override
    public DataSetIterator transform(List<Quote> quoteList) {
        Quote[] quotes = quoteList.toArray(new Quote[0]);
        int numberOfQuotes = quotes.length - 1;
        double[] closes = new double[numberOfQuotes];
        double[] volumes = new double[numberOfQuotes];
        double[] outputCloses = new double[numberOfQuotes];

        for (int i = 0; i < quotes.length - 2; i++) {
            closes[i] = quotes[i].getClose().doubleValue();
            volumes[i] = quotes[i].getVolume().doubleValue();
            outputCloses[i] = quotes[i+1].getClose().doubleValue();
        }

        INDArray closeNDArray = Nd4j.create(closes, numberOfQuotes, 1);
        INDArray volumeNDArray = Nd4j.create(volumes, numberOfQuotes, 1);
        INDArray inputNDArray = Nd4j.hstack(closeNDArray, volumeNDArray);
        INDArray outPut = Nd4j.create(outputCloses, numberOfQuotes, 1);
        DataSet dataSet = new DataSet(inputNDArray, outPut);
        List<DataSet> listDs = dataSet.asList();
        Collections.shuffle(listDs, new Random(12345));
        return new ListDataSetIterator<>(listDs, SAMPLE_WINDOW);
    }
}
