package com.alok.number.processor.compute;

import com.alok.number.processor.compute.task.*;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Builder
public class AsyncProcessor implements Processor {

    private Set<Integer> numbers;

    @Override
    public void process() {
        List sortedNumbers = null;
        try {
            sortedNumbers = validateAndOptimize(numbers);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }
        CyclicBarrier barrier = new CyclicBarrier(3, SummaryPrinter.builder().numbers(sortedNumbers).type("PARALLEL").build());

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

        //TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        tasks.forEach(task -> executorService.execute(task));
        executorService.shutdown();
    }
}
