package com.alok.number.processor.compute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface Processor {
    void process();

    default List<Integer> validateAndOptimize(Set<Integer> numbers) {

        if (numbers == null || numbers.isEmpty())
            throw new IllegalArgumentException("Invalid Arguments");

        if (numbers.size() < 2)
            throw new IllegalArgumentException("At least 2 numbers must be different");

        if (numbers.size() > 5)
            throw new IllegalArgumentException("Number of unique numbers can be more than 5");

        List<Integer> sortedNumbers = new ArrayList<>();
        numbers.forEach(number -> {
            if (number < 0 || number > 10)
                throw new IllegalArgumentException("Invalid Arguments");

            sortedNumbers.add(number);
        });

        Collections.sort(sortedNumbers, (a, b) -> b.compareTo(a));

        return sortedNumbers;
    }
}
