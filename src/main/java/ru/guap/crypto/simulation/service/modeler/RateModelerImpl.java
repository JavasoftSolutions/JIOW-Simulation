package ru.guap.crypto.simulation.service.modeler;

import lombok.RequiredArgsConstructor;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.stereotype.Service;
import ru.guap.crypto.simulation.dto.QuoteDto;
import ru.guap.crypto.simulation.service.transform.TransformService;
import ru.guap.crypto.simulation.tool.Utility;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RateModelerImpl implements RateModeler {

    public static final int SEED = 12345;
    public static final int N_EPOCHS = 200;
    public static final double LEARNING_RATE = 0.01;

    private final TransformService transformService;

    @Override
    public void prepareModel(String quote, List<QuoteDto> result) {
        DataSetIterator iterator = transformService.transform(result);

        //Create the network
        int numInput = 2;
        int numOutputs = 1;
        int nHidden = 10;
        MultiLayerNetwork net = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .seed(SEED)
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs(LEARNING_RATE, 0.9))
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
        for (int i = 0; i < N_EPOCHS; i++) {
            iterator.reset();
            net.fit(iterator);
        }
        String fileName = getFileName(quote);
        try {
            net.save(new File(fileName));
        } catch (IOException ex) {
            throw new RuntimeException("Unable to write file: " + fileName);
        }
    }

    @Override
    public MultiLayerNetwork getPreparedModel(String quote) {
        String fileName = getFileName(quote);
        try {
            return MultiLayerNetwork.load(new File(fileName), false);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load file: " + fileName);
        }
    }

    private String getFileName(String quote) {
        Objects.nonNull(quote);
        return String.format("%s.%s", quote , Utility.MODEL_FILE_EXT);
    }

}
