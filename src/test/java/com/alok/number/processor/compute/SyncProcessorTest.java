package com.alok.number.processor.compute;

import com.alok.number.processor.compute.task.Adder;
import com.alok.number.processor.compute.task.Multiplier;
import com.alok.number.processor.compute.task.Subtracter;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SyncProcessorTest {
    @Mocked
    Adder adder;

    @Mocked
    Multiplier multiplier;

    @Mocked
    Subtracter subtracter;

    @Test
    void validateAndOptimize_EmptyCollection() {
        Processor processor = SyncProcessor.builder().build();
        assertThrows(IllegalArgumentException.class, () -> processor.validateAndOptimize(Collections.emptySet()));
    }

    @Test
    void validateAndOptimize_NoTwoUniqueNumbers() {
        Processor processor = SyncProcessor.builder().build();
        assertThrows(IllegalArgumentException.class, () -> processor.validateAndOptimize(
                new HashSet<Integer>() {{
                    add(1);
                    add(1);
                    add(1);
                    add(1);
                    add(1);
                }})
        );
    }

    @Test
    void validateAndOptimize_NumberOutOfRange() {
        Processor processor = SyncProcessor.builder().build();
        assertThrows(IllegalArgumentException.class, () -> processor.validateAndOptimize(
                new HashSet<Integer>() {{
                    add(1);
                    add(-1);
                    add(2);
                    add(3);
                    add(4);
                }})
        );

        assertThrows(IllegalArgumentException.class, () -> processor.validateAndOptimize(
                new HashSet<Integer>() {{
                    add(1);
                    add(11);
                    add(2);
                    add(3);
                    add(4);
                }})
        );
    }

    @Test
    void validateAndOptimize() {
        Processor processor = SyncProcessor.builder().build();
        List<Integer> result = processor.validateAndOptimize(
                new HashSet<Integer>() {{
                    add(5);
                    add(1);
                    add(3);
                    add(1);
                    add(2);
                }});

        assertEquals(4, result.size());
        assertEquals(java.util.Optional.of(5).get(), result.get(0));
    }

    @Test
    void process() {
        Processor processor = SyncProcessor.builder().build();
        assertDoesNotThrow(() -> processor.process());

    }
}