package com.alok.number.processor.compute.task;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AdderTest {

    @org.junit.jupiter.api.Test
    void compute() {
        Adder adder = Adder.builder().numbers(Arrays.asList(1, 6 ,8 , 9, 10)).build();
        assertEquals("Add: 34", adder.compute());
    }

}