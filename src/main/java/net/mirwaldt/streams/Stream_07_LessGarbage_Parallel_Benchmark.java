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

Benchmark                                                      Mode  Cnt    Score   Error  Units
Benchmark_07_LessGarbage_2.reduceWithStringBuildersParallel    avgt   25  102.328 ± 0.355  ms/op
Benchmark_07_LessGarbage_2.reduceWithStringBuildersSequential  avgt   25  224.638 ± 3.675  ms/op

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
