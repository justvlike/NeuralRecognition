package com.uladzislaum.services;

import com.uladzislaum.exception.InvalidDataException;
import com.uladzislaum.model.Layer;
import com.uladzislaum.model.NeuralNetwork;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Random;

public class NeuralSolver {

    private final double LEARN_SPEED = 0.01;

    public void separator() {
        System.out.println("\n==============================\n");
    }

    public NeuralNetwork randomizeNetwork(NeuralNetwork network) {
        Layer[] layers = network.getLayers();
        Layer[] randomizedLayers = new Layer[network.getLayers().length];
        for (int i = 0; i < layers.length; i++) {
            randomizedLayers[i] = randomizeWeights(layers[i]);
        }
        network.setLayers(randomizedLayers);
        return network;
    }

    public void recognizeAndVisualise(double[] inputs, NeuralNetwork network, List<String> names) {
        getVisualisation(network, names, recognize(inputs, network));
    }

    public <T> T recognizeEntity(double[] inputs, NeuralNetwork network, Map<Integer, T> entitymap) {
        return entitymap.get(recognize(inputs, network));
    }

    private int recognize(double[] inputs, NeuralNetwork network) {
        double[] outputs = calculateNetwork(inputs, network);
        return getMaxValueIndex(outputs);
    }

    private double[] calculateNetwork(double[] inputs, NeuralNetwork network) {
        for (Layer layer : network.getLayers()) {
            inputs = calculateLayerActivations(inputs, layer);
        }
        return inputs;
    }
    private double[] calculateLayer(double[] inputs, Layer layer) {
        verifyInputsCount(inputs, layer);
        double[] outputs = new double[layer.getNodesOutCount()];
        for(int nodeOut = 0; nodeOut < layer.getNodesOutCount(); nodeOut++) {
            double output = layer.getBiases()[nodeOut];
            for(int nodeIn = 0; nodeIn < layer.getNodesInCount(); nodeIn++) {
                output += inputs[nodeIn] * layer.getWeights()[nodeIn][nodeOut];
            }
            outputs[nodeOut] = output;
        }
        return outputs;
    }

    private double[] calculateLayerActivations(double[] inputs, Layer layer) {
        return sigmoid(calculateLayer(inputs, layer));
    }

    @SneakyThrows
    private void verifyInputsCount(double[] inputs, Layer layer) {
        if(inputs.length != layer.getNodesInCount()) {
            throw new InvalidDataException("invalid inputs provided");
        }
    }

    private int getMaxValueIndex(double[] doubles) {
        int maxAt = 0;
        for (int i = 0; i < doubles.length; i++) {
            maxAt = doubles[i] > doubles[maxAt] ? i : maxAt;
        }
        return maxAt;
    }

    private double[] sigmoid(double[] doubles) {
        double[] outputs = new double[doubles.length];
        for (int i = 0; i < doubles.length; i++) {
            outputs[i] = 1 / (1 + Math.exp(-doubles[i]));
        }
        return outputs;
    }

    private void getVisualisation(NeuralNetwork network, List<String> names, int answer) {
        List<Integer> layerSizes = new java.util.ArrayList<>();
        layerSizes.add(network.getLayers()[0].getNodesInCount());
        layerSizes.addAll(
                Arrays.stream(network.getLayers())
                .toList()
                .stream()
                .map(Layer::getNodesOutCount)
                .toList());
        int maxSize = layerSizes.stream().max(Integer::compare).orElse(0);
        Stack<String> namesStack = new Stack<>();
        for (String name : names) {
            namesStack.push(name);
        }

        for (int i = 0; i < maxSize; i++) {
            StringBuilder line = new StringBuilder();
            for (Integer layerSize : layerSizes) {
                line.append(layerSize - i > 0 ? "*   " : "    ");
            }
            if(!namesStack.isEmpty()) {
                line.append(namesStack.pop());
            }
            if(i == answer) {
                line.append(" - Solution");
            }
            System.out.println(line);
        }
    }

    private Layer randomizeWeights(Layer layer) {
        Random random = new Random();
        double[][] weights = new double[layer.getWeights().length][layer.getWeights()[0].length];
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = random.nextDouble();
            }
        }
        layer = new Layer(layer.getNodesInCount(), layer.getNodesOutCount(), weights, generateBias(layer, 0));
        return layer;
    }
    private double[] generateBias(Layer layer, double bias) {
        double[] biases = new double[layer.getBiases().length];
        Arrays.fill(biases, bias);
        return biases;
    }

    private void trainNetwork(NeuralNetwork network, double[] inputs, double[] outputs) {
        double[] loopOutputs = calculateNetwork(inputs, network);
        double[] errors = new double[outputs.length];
        double[] nodeCosts = new double[outputs.length];
        for(int i = 0; i < outputs.length; i++) {
            errors[i] = outputs[i] - loopOutputs[i];
            nodeCosts[i] = Math.pow((loopOutputs[i] - outputs[i]), 2);
        }
        double cost = Arrays.stream(nodeCosts).sum();

        //sigmoid(error*weight) * LEARN_SPEED
    }
}
