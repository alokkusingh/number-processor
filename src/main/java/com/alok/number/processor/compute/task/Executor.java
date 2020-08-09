package com.alok.number.processor.compute.task;

import lombok.Builder;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Builder
public class Executor implements Runnable {
    private CyclicBarrier barrier;
    private Computer computer;


    @Override
    public void run() {
        System.out.println(computer.compute());
        try {
            if (barrier != null)
                barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }


}
