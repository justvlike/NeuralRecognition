package com.uladzislaum.model;

import lombok.Getter;

@Getter
public class Layer {
    private final int nodesInCount;
    private final int nodesOutCount;
    private final double[][] weights;
    private final double[] biases;

    public Layer(int nodesInCount, int nodesOutCount) {
        this.nodesInCount = nodesInCount;
        this.nodesOutCount = nodesOutCount;

        weights = new double[nodesInCount][nodesOutCount];
        biases = new double[nodesOutCount];
    }

    public Layer(int nodesInCount, int nodesOutCount, double[][] weights) {
        this.nodesInCount = nodesInCount;
        this.nodesOutCount = nodesOutCount;

        this.weights = weights;
        biases = new double[nodesOutCount];
    }

    public Layer(int nodesInCount, int nodesOutCount, double[] biases) {
        this.nodesInCount = nodesInCount;
        this.nodesOutCount = nodesOutCount;

        weights = new double[nodesInCount][nodesOutCount];
        this.biases = biases;
    }

    public Layer(int nodesInCount, int nodesOutCount, double[][] weights, double[] biases) {
        this.nodesInCount = nodesInCount;
        this.nodesOutCount = nodesOutCount;

        this.weights = weights;
        this.biases = biases;
    }
}
