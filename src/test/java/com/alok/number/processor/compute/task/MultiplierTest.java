package com.alok.number.processor.compute.task;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiplierTest {

    @org.junit.jupiter.api.Test
    void compute() {
        Multiplier multiplier = Multiplier.builder().numbers(Arrays.asList(1, 6, 8 , 9, 10)).build();
        assertEquals("Multi: 4320", multiplier.compute());
    }

}