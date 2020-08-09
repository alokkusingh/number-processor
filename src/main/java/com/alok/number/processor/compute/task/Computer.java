package com.alok.number.processor.compute.task;

public interface Computer {

    String compute();

    default void work() {
        try {
            Thread.sleep((int) ((Math.random() * 9000) + 1000));
        } catch (InterruptedException e) {
        }
    }
}
