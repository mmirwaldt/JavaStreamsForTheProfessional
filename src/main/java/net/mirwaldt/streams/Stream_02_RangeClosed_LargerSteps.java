package net.mirwaldt.streams;

import java.util.stream.IntStream;

import static net.mirwaldt.streams.util.Collatz.collatzMaxSteps;

public class Stream_02_RangeClosed_LargerSteps {
    public static void main(String[] args) {
        long multiplesOf3WithCollatz500 = IntStream
                .rangeClosed(1, 10_000_000)
                .map(i -> i * 3) // in steps of 3, i.e. 3, 6, 9, ...
                .parallel()
                .filter(i -> collatzMaxSteps(i, 501) == 500)
                .count();
        System.out.println(multiplesOf3WithCollatz500); // output: 22
    }
}
