package com.uladzislaum.services;

import com.uladzislaum.model.Layer;
import com.uladzislaum.model.NeuralNetwork;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class NeuralDrawer {
    protected static void getVisualisation(NeuralNetwork network, List<String> names, int answer) {
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
}
