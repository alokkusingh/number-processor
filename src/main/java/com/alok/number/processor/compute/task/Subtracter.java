package com.alok.number.processor.compute.task;

import lombok.Builder;

import java.util.List;

@Builder
public class Subtracter implements Computer {
    private List<Integer> numbers;

    @Override
    public String compute() {
        work();
        int initial = numbers.get(0) * 2;
        return "Sub: " + numbers.stream().reduce(initial, (a, b) -> a - b);
    }
}
