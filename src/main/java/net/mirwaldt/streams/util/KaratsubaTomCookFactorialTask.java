package net.mirwaldt.streams.util;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.BinaryOperator;

import static java.math.BigInteger.ONE;
import static net.mirwaldt.streams.util.KaratsubaTomCook.accumulate;

public class KaratsubaTomCookFactorialTask extends RecursiveTask<BigInteger> {
    private final int start;
    private final int end;
    private final int minLength;
    private final BinaryOperator<BigInteger> multiply;

    public KaratsubaTomCookFactorialTask(int start, int end, int minLength, BinaryOperator<BigInteger> multiply) {
        this.start = start;
        this.end = end;
        this.minLength = minLength;
        this.multiply = multiply;
    }

    @Override
    protected BigInteger compute() {
        int length = end - start;
        if (length <= minLength) {
            BigInteger[] result = new BigInteger[]{ONE, ONE, ONE};
            for (int i = start; i < end; i++) {
                accumulate(result, BigInteger.valueOf(i), multiply);
            }
            return result[0].multiply(result[1]).multiply(result[2]);
        } else {
            int halfLength = length / 2;
            KaratsubaTomCookFactorialTask leftTask =
                    new KaratsubaTomCookFactorialTask(start, start + halfLength, minLength, multiply);
            leftTask.fork();
            KaratsubaTomCookFactorialTask rightTask =
                    new KaratsubaTomCookFactorialTask(start + halfLength, end, minLength, multiply);
            return multiply.apply(rightTask.compute(), leftTask.join());
        }
    }

    public static BigInteger forkJoinPool(
            int n, int minLength, BinaryOperator<BigInteger> multiply) {
        return ForkJoinPool.commonPool()
                .invoke(new KaratsubaTomCookFactorialTask(1, n + 1, minLength, multiply));
    }
}
