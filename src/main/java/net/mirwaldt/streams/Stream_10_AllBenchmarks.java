package net.mirwaldt.streams;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Stream_10_AllBenchmarks {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Stream_01_RangeClosed_Benchmark.class.getSimpleName())
                .include(Stream_04_FindAny_Benchmark.class.getSimpleName())
                .include(Stream_05_Locality_Benchmark.class.getSimpleName())
                .include(Stream_06_LessGarbage_Sequential_Benchmark.class.getSimpleName())
                .include(Stream_07_LessGarbage_Parallel_Benchmark.class.getSimpleName())
                .include(Stream_08_ParallelFactorial_Benchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
