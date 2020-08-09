package com.alok.number.processor.compute.task;

import lombok.Builder;

import java.util.List;

@Builder
public class Multiplier implements Computer {
    private List<Integer> numbers;

    @Override
    public String compute() {
        work();
        return "Multi: " + numbers.stream().reduce(1, (a, b) -> a * b);
    }
}
