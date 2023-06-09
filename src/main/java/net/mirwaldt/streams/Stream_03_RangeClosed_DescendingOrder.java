package net.mirwaldt.streams;

import java.util.stream.IntStream;

import static net.mirwaldt.streams.util.Collatz.collatzMaxSteps;

public class Stream_03_RangeClosed_DescendingOrder {
    public static void main(String[] args) {
        int highestCollatz500 = IntStream
                .rangeClosed(1, 10_000_000)
                .map(i -> 10_000_000 - i + 1) // reverses the order
                .parallel()
                .filter(i -> collatzMaxSteps(i, 501) == 500)
                .findFirst().orElse(-1);
        System.out.println(highestCollatz500); // output: 3030267
    }
}
