package net.mirwaldt.streams.util;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.BinaryOperator;

public class FactorialTask extends RecursiveTask<BigInteger> {
    private final int start;
    private final int end;
    private final BinaryOperator<BigInteger> multiply;

    public FactorialTask(int n, BinaryOperator<BigInteger> multiply) {
        this.start = 1;
        this.end = n + 1;
        this.multiply = multiply;
    }

    public FactorialTask(int start, int end, BinaryOperator<BigInteger> multiply) {
        this.start = start;
        this.end = end;
        this.multiply = multiply;
    }

    @Override
    protected BigInteger compute() {
        int length = end - start;
        if (length == 1) {
            return BigInteger.valueOf(start);
        } else {
            int halfLength = length / 2;
            FactorialTask leftTask = new FactorialTask(start, start + halfLength, multiply);
            leftTask.fork();
            FactorialTask rightTask = new FactorialTask(start + halfLength, end, multiply);
            return multiply.apply(rightTask.compute(), leftTask.join());
        }
    }

    public static BigInteger forkJoinPool(int n, BinaryOperator<BigInteger> multiply) {
        return ForkJoinPool.commonPool().invoke(new FactorialTask(n, multiply));
    }
}