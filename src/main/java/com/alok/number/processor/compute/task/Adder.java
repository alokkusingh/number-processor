package com.alok.number.processor.compute.task;

import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Builder
public class Adder implements Computer {
    private List<Integer> numbers;

    @Override
    public String compute() {
        work();
        return "Add: " + numbers.stream().reduce(0, (a, b) -> a + b);
    }
}
