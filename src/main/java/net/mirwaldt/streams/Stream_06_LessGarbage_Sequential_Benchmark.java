package net.mirwaldt.streams;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static java.lang.Character.*;
import static net.mirwaldt.streams.util.AlchemicalReduce.*;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Stream_06_LessGarbage_Sequential_Benchmark {

    /*
Summary:
Benchmark                                                                           Mode  Cnt       Score   Error    Units
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders                 avgt   25       2.204 ± 0.022    ms/op
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders:·gc.alloc.rate  avgt   25      88.466 ± 0.846   MB/sec
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings                        avgt   25     709.272 ± 0.351    ms/op
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings:·gc.alloc.rate         avgt   25   12855.164 ± 6.082   MB/sec

All:
Benchmark                                                                                             Mode  Cnt            Score          Error   Units
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders                                   avgt   25            2.204 ±        0.022   ms/op
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders:·gc.alloc.rate                    avgt   25           88.466 ±        0.846  MB/sec
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders:·gc.alloc.rate.norm               avgt   25       214692.183 ±        2.680    B/op
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders:·gc.churn.G1_Eden_Space           avgt   25           85.469 ±       53.353  MB/sec
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders:·gc.churn.G1_Eden_Space.norm      avgt   25       207273.027 ±   129400.579    B/op
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders:·gc.churn.G1_Survivor_Space       avgt   25            0.001 ±        0.001  MB/sec
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders:·gc.churn.G1_Survivor_Space.norm  avgt   25            2.064 ±        3.541    B/op
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders:·gc.count                         avgt   25           15.000                 counts
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStringBuilders:·gc.time                          avgt   25           20.000                     ms
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings                                          avgt   25          709.272 ±        0.351   ms/op
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings:·gc.alloc.rate                           avgt   25        12855.164 ±        6.082  MB/sec
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings:·gc.alloc.rate.norm                      avgt   25  10010461679.339 ±      510.016    B/op
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings:·gc.churn.G1_Eden_Space                  avgt   25        12938.713 ±       53.248  MB/sec
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings:·gc.churn.G1_Eden_Space.norm             avgt   25  10075512328.533 ± 39677603.948    B/op
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings:·gc.churn.G1_Survivor_Space              avgt   25            0.060 ±        0.001  MB/sec
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings:·gc.churn.G1_Survivor_Space.norm         avgt   25        46841.472 ±     1148.865    B/op
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings:·gc.count                                avgt   25         2414.000                 counts
Stream_06_LessGarbage_Sequential_Benchmark.reduceWithStrings:·gc.time                                 avgt   25         1448.000                     ms
     */

    String input = createString(200_000);

    @Benchmark
    public String reduceWithStrings() {
        return input.chars()
                .mapToObj(c -> (char) c)
                .reduce("",
                        (result, c) -> reduce(result, c),
                        (left, right) -> combine(left, right));
    }

    @Benchmark
    public String reduceWithStringBuilders() {
        return input.chars()
                .collect(StringBuilder::new,
                        (result, i) -> reduce(result, (char) i),
                        (left, right) -> combine(left, right))
                .toString();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Stream_06_LessGarbage_Sequential_Benchmark.class.getSimpleName())
                .addProfiler(GCProfiler.class)
                .build();
        new Runner(opt).run();
    }
}
