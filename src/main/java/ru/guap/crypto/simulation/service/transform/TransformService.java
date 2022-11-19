package ru.guap.crypto.simulation.service;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.List;
import java.util.Queue;

public interface TransformService {

    INDArray transform(List<Queue> queueList);
}
