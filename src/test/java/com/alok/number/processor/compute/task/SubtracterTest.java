package com.alok.number.processor.compute.task;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtracterTest {

    @org.junit.jupiter.api.Test
    void compute() {
        Subtracter subtracter = Subtracter.builder().numbers(Arrays.asList(6, 5, 4, 3, 1)).build();
        assertEquals("Sub: -7", subtracter.compute());
    }

}