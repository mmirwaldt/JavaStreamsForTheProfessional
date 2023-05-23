package net.mirwaldt.streams;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static net.mirwaldt.streams.util.AlchemicalReduce.*;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Stream_07_LessGarbage_Parallel_Benchmark {

    /*
        The parallel stream can benefit from the approach with the string builder:

        Benchmark                                                                   Mode  Cnt    Score   Error  Units
        Stream_07_LessGarbage_Parallel_Benchmark.reduceWithStringBuildersParallel    avgt   25  102.313 ± 0.617  ms/op
        Stream_07_LessGarbage_Parallel_Benchmark.reduceWithStringBuildersSequential  avgt   25  229.569 ± 4.175  ms/op
     */

    String input = createString(20_000_000);

    @Benchmark
    public String reduceWithStringBuildersSequential() {
        return input.chars()
                .collect(StringBuilder::new,
                        (result, i) -> reduce(result, (char) i),
                        (left, right) -> combine(left, right))
                .toString();
    }

    @Benchmark
    public String reduceWithStringBuildersParallel() {
        return input.chars()
                .parallel()
                .collect(StringBuilder::new,
                        (result, i) -> reduce(result, (char) i),
                        (left, right) -> combine(left, right))
                .toString();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Stream_07_LessGarbage_Parallel_Benchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
