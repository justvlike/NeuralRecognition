package com.uladzislaum.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NeuralNetwork {
    Layer[] layers;
    public NeuralNetwork(int[] layerSizes) {
        layers = new Layer[layerSizes.length-1];
        for(int i = 0; i < layers.length; i++) {
            layers[i] = new Layer(layerSizes[i], layerSizes[i+1]);
        }
    }
}
