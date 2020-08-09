package com.alok.number.processor.compute;

import com.alok.number.processor.compute.task.*;
import lombok.Builder;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Builder
public class SyncProcessor implements Processor {

    protected String type;
    protected long startTime;
    protected Set<Integer> numbers;

    @Builder.Default
    private final TaskExecutor taskExecutor = new SyncTaskExecutor();

    @Override
    public void process() {
        List sortedNumbers = null;
        try {
            sortedNumbers = validateAndOptimize(numbers);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        List<Runnable> tasks = Arrays.asList(
                Executor.builder()
                        .computer(Adder.builder().numbers(sortedNumbers).build())
                        .build(),
                Executor.builder()
                        .computer(Multiplier.builder().numbers(sortedNumbers).build())
                        .build(),
                Executor.builder()
                        .computer(Subtracter.builder().numbers(sortedNumbers).build())
                        .build(),
                SummaryPrinter.builder().numbers(sortedNumbers).type("SEQUENTIAL").build()
        );

        tasks.forEach(task -> taskExecutor.execute(task));
    }
}
