package com.alok.number.processor.compute;

import com.alok.number.processor.compute.task.*;
import lombok.Builder;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

@Builder
public class AsyncProcessor implements Processor {

    protected String type;
    protected long startTime;
    protected Set<Integer> numbers;

    @Builder.Default
    private final TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
    private CyclicBarrier barrier;

    @Override
    public void process() {
        List sortedNumbers = null;
        try {
            sortedNumbers = validateAndOptimize(numbers);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }
        barrier = new CyclicBarrier(3, SummaryPrinter.builder().numbers(sortedNumbers).type("PARALLEL").build());

        List<Runnable> tasks = Arrays.asList(
                Executor.builder()
                        .computer(Adder.builder().numbers(sortedNumbers).build())
                        .barrier(barrier)
                        .build(),
                Executor.builder()
                        .computer(Multiplier.builder().numbers(sortedNumbers).build())
                        .barrier(barrier)
                        .build(),
                Executor.builder()
                        .computer(Subtracter.builder().numbers(sortedNumbers).build())
                        .barrier(barrier)
                        .build()
        );

        tasks.forEach(task -> taskExecutor.execute(task));
    }
}
