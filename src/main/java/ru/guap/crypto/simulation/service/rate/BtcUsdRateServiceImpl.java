package ru.guap.crypto.simulation.service.rate;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.guap.crypto.simulation.dto.Quote;
import ru.guap.crypto.simulation.mapper.QuoteMapper;
import ru.guap.crypto.simulation.service.client.RestClient;
import ru.guap.crypto.simulation.service.transform.TransformService;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service("BTC-USD")
public class BtcUsdRateServiceImpl implements RateService {

    private static final String QUOTE = "BTC-USD";
    public static final int seed = 12345;
    //Number of epochs (full passes of the data)
    public static final int nEpochs = 200;
    //Network learning rate
    public static final double learningRate = 0.01;

    private final QuoteMapper mapper;
    private final RestClient restClient;
    private final TransformService transformService;

    @Override
    public String getRates(LocalDateTime from, LocalDateTime to, String interval) throws IOException {
        String stringWithQuotes = restClient.getQuoteString(QUOTE, from, to, interval);
        CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new StringReader(stringWithQuotes));
        List<Quote> result = new ArrayList<>();
        for (CSVRecord record : parser) {
            result.add(mapper.map(record));
        }
        DataSetIterator iterator = transformService.transform(result);

        //Create the network
        int numInput = 2;
        int numOutputs = 1;
        int nHidden = 10;
        MultiLayerNetwork net = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs(learningRate, 0.9))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInput).nOut(nHidden)
                        .activation(Activation.TANH) //Change this to RELU and you will see the net learns very well very quickly
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(nHidden).nOut(numOutputs).build())
                .build()
        );
        net.init();
        net.setListeners(new ScoreIterationListener(1));

        //Train the network on the full data set, and evaluate in periodically
        for (int i = 0; i < nEpochs; i++) {
            iterator.reset();
            net.fit(iterator);
        }

        final INDArray input = Nd4j.create(new double[]{16_601_76, 22_712_059_904.00}, 1, 2);
        INDArray out = net.output(input, false);

        return out.toStringFull();
    }
}
