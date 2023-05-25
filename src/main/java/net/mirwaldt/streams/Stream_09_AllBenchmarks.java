package net.mirwaldt.streams;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Stream_09_AllBenchmarks {
    /*
        Benchmark                                                                    Mode  Cnt    Score   Error  Units
        Stream_01_RangeClosed_Benchmark.filterLimitParallelStream                    avgt   25   75.269 ± 0.769  ms/op
        Stream_01_RangeClosed_Benchmark.rangeClosedParallelStream                    avgt   25   53.315 ± 0.071  ms/op
        Stream_04_FindAny_Benchmark.findAnyParallelStream                            avgt   25    9.981 ± 0.139  ms/op
        Stream_04_FindAny_Benchmark.findFirstParallelStream                          avgt   25   31.035 ± 0.244  ms/op
        Stream_06_LessGarbage_Parallel_Benchmark.reduceWithStringBuildersParallel    avgt   25  102.313 ± 0.617  ms/op
        Stream_06_LessGarbage_Parallel_Benchmark.reduceWithStringBuildersSequential  avgt   25  229.569 ± 4.175  ms/op
        Stream_07_ParallelFactorial_Benchmark.forkJoinPool                           avgt   25   16.564 ± 0.022  ms/op
        Stream_07_ParallelFactorial_Benchmark.forkJoinPoolKaratsubaTomCook           avgt   25   23.108 ± 0.037  ms/op
        Stream_07_ParallelFactorial_Benchmark.parallelStream                         avgt   25   39.100 ± 0.026  ms/op
        Stream_07_ParallelFactorial_Benchmark.parallelStreamKaratsubaTomCook         avgt   25   18.728 ± 0.060  ms/op
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Stream_01_RangeClosed_Benchmark.class.getSimpleName())
                .include(Stream_04_FindAny_Benchmark.class.getSimpleName())
                .include(Stream_06_LessGarbage_Parallel_Benchmark.class.getSimpleName())
                .include(Stream_07_ParallelFactorial_Benchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
