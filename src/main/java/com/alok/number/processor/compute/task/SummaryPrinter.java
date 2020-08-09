package com.alok.number.processor.compute.task;

import lombok.Builder;

import java.util.List;

@Builder
 public class SummaryPrinter implements Runnable {
    List<Integer> numbers;
    String type;
    @Builder.Default
    long startTime = System.currentTimeMillis();

    @Override
    public void run() {
         System.out.println("Time: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
         System.out.println("Type: " + type);
         System.out.println("Numbers: " + numbers);
   }
}
