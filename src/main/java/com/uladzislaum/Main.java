package com.uladzislaum;

import com.uladzislaum.model.NeuralNetwork;
import com.uladzislaum.services.NeuralSolver;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        NeuralSolver solver = new NeuralSolver();
        solver.recognizeAndVisualise(
                new double[]{0.4, 0.5},
                solver.randomizeNetwork(new NeuralNetwork(new int[]{2, 3, 2})),
                List.of("Red", "Blue"));
        solver.separator();
        solver.recognizeAndVisualise(
                new double[]{0.4, 0.5, 0.6},
                solver.randomizeNetwork(new NeuralNetwork(new int[]{3, 4, 4, 3})),
                List.of("Red", "Green", "Blue"));
    }
}
