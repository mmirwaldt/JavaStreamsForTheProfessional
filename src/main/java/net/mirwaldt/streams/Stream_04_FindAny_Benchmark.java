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
public class Stream_04_FindAny_Benchmark {
    public static final int N = 1_000_000;
    public static final int M = 400;

    /*
        This benchmarks shows findAny() can be faster than findFirst().

        Benchmark                                               Mode  Cnt   Score   Error  Units
        Stream_04_FindAny_Benchmark.findAnyParallelStream       avgt   25    9.981 ± 0.139  ms/op
        Stream_04_FindAny_Benchmark.findFirstParallelStream     avgt   25   31.035 ± 0.244  ms/op
     */

    @Benchmark
    public int findAnyParallelStream() {
        return IntStream
                .rangeClosed(1, N)
                .filter(i -> collatzMaxSteps(i, M + 1) == M)
                .parallel()
                .findAny().orElse(-1);
    }

    @Benchmark
    public int findFirstParallelStream() {
        return IntStream
                .rangeClosed(1, N)
                .filter(i -> collatzMaxSteps(i, M + 1) == M)
                .parallel()
                .findFirst().orElse(-1);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Stream_04_FindAny_Benchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
