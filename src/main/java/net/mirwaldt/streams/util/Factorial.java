package net.mirwaldt.streams.util;

import java.math.BigInteger;
import java.util.function.BinaryOperator;
import java.util.stream.LongStream;

import static java.math.BigInteger.ONE;

public class Factorial {
    public static BigInteger parallelStream(int n, BinaryOperator<BigInteger> multiply) {
        return LongStream.rangeClosed(2, n)
                .mapToObj(BigInteger::valueOf)
                .parallel()
                .reduce(multiply)
                .orElse(ONE);
    }
}
