package net.mirwaldt.streams;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static net.mirwaldt.streams.util.Collatz.collatzMaxSteps;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Stream_01_RangeClosed_Benchmark {
    public static final int N = 5_000_000;
    public static final int S = 25;

    /*
        This benchmarks shows rangeClosed() can be faster than an infinite stream with limit() for parallel streams.

        Benchmark                                           Mode  Cnt   Score   Error  Units
        Benchmark_01_RangeClosed.filterLimitParallelStream  avgt   25  74.428 ± 0.169  ms/op
        Benchmark_01_RangeClosed.rangeClosedParallelStream  avgt   25  52.996 ± 0.216  ms/op
     */

    @Benchmark
    public long filterLimitParallelStream() {
        return IntStream
                .iterate(1, i -> i + 1)
                .limit(N)
                .filter(i -> collatzMaxSteps(i, S + 1) <= S)
                .parallel()
                .count();
    }

    @Benchmark
    public long rangeClosedParallelStream() {
        return IntStream
                .rangeClosed(1, N)
                .filter(i -> collatzMaxSteps(i, S + 1) <= S)
                .parallel()
                .count();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Stream_01_RangeClosed_Benchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
