package net.mirwaldt.streams;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Stream_10_AllBenchmarks {
    /*
        Benchmark                                                                    Mode  Cnt    Score   Error  Units
        Stream_01_RangeClosed_Benchmark.filterLimitParallelStream                    avgt   25   75.269 ± 0.769  ms/op
        Stream_01_RangeClosed_Benchmark.rangeClosedParallelStream                    avgt   25   53.315 ± 0.071  ms/op
        Stream_04_FindAny_Benchmark.findAnyParallelStream                            avgt   25    9.981 ± 0.139  ms/op
        Stream_04_FindAny_Benchmark.findFirstParallelStream                          avgt   25   31.035 ± 0.244  ms/op
        Stream_05_Locality_Benchmark.sumArrayListParallel                            avgt   25   14.918 ± 1.578  ms/op
        Stream_05_Locality_Benchmark.sumArrayListSequential                          avgt   25   23.959 ± 0.041  ms/op
        Stream_05_Locality_Benchmark.sumArrayParallel                                avgt   25    2.610 ± 0.003  ms/op
        Stream_05_Locality_Benchmark.sumArraySequential                              avgt   25    6.640 ± 0.006  ms/op
        Stream_05_Locality_Benchmark.sumSortedArrayListParallel                      avgt   25   65.853 ± 0.043  ms/op
        Stream_05_Locality_Benchmark.sumSortedArrayListSequential                    avgt   25  204.988 ± 0.941  ms/op
        Stream_07_LessGarbage_Parallel_Benchmark.reduceWithStringBuildersParallel    avgt   25  102.313 ± 0.617  ms/op
        Stream_07_LessGarbage_Parallel_Benchmark.reduceWithStringBuildersSequential  avgt   25  229.569 ± 4.175  ms/op
        Stream_08_ParallelFactorial_Benchmark.forkJoinPool                           avgt   25   16.564 ± 0.022  ms/op
        Stream_08_ParallelFactorial_Benchmark.forkJoinPoolKaratsubaTomCook           avgt   25   23.108 ± 0.037  ms/op
        Stream_08_ParallelFactorial_Benchmark.parallelStream                         avgt   25   39.100 ± 0.026  ms/op
        Stream_08_ParallelFactorial_Benchmark.parallelStreamKaratsubaTomCook         avgt   25   18.728 ± 0.060  ms/op
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Stream_01_RangeClosed_Benchmark.class.getSimpleName())
                .include(Stream_04_FindAny_Benchmark.class.getSimpleName())
                .include(Stream_05_Locality_Benchmark.class.getSimpleName())
                .include(Stream_07_LessGarbage_Parallel_Benchmark.class.getSimpleName())
                .include(Stream_08_ParallelFactorial_Benchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
