package net.mirwaldt.streams;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Stream_05_Locality_Benchmark {
    /*
        Benchmark                                                   Mode  Cnt    Score   Error  Units
        Stream_05_Locality_Benchmark.sumArrayListParallel           avgt   25   14.918 ± 1.578  ms/op
        Stream_05_Locality_Benchmark.sumArrayListSequential         avgt   25   23.959 ± 0.041  ms/op
        Stream_05_Locality_Benchmark.sumArrayParallel               avgt   25    2.610 ± 0.003  ms/op
        Stream_05_Locality_Benchmark.sumArraySequential             avgt   25    6.640 ± 0.006  ms/op
        Stream_05_Locality_Benchmark.sumSortedArrayListParallel     avgt   25   65.853 ± 0.043  ms/op
        Stream_05_Locality_Benchmark.sumSortedArrayListSequential   avgt   25  204.988 ± 0.941  ms/op
     */

    final int N = 25_000_000;

    int[] array;
    private ArrayList<Integer> arrayList;
    private ArrayList<Integer> sortedArrayList;

    @Setup
    public void setup() {
        Random random = new Random(1234); // We always want the same random numbers
        arrayList = new ArrayList<>(N);
        array = new int[N];
        for (int i = 0; i < N; i++) {
            int n = random.nextInt(1_000_000);
            array[i] = n;
            arrayList.add(n);
        }
        sortedArrayList = new ArrayList<>(arrayList);
        Collections.sort(sortedArrayList);
    }

    @Benchmark
    public long sumArrayListSequential() {
        return sumArrayListSequential(arrayList);
    }

    @Benchmark
    public long sumArrayListParallel() {
        return sumArrayListParallel(arrayList);
    }

    @Benchmark
    public long sumArraySequential() {
        return sumArraySequential(array);
    }

    @Benchmark
    public long sumArrayParallel() {
        return sumArrayParallel(array);
    }

    @Benchmark
    public long sumSortedArrayListSequential() {
        return sumSortedArrayListSequential(sortedArrayList);
    }

    @Benchmark
    public long sumSortedArrayListParallel() {
        return sumSortedArrayListParallel(sortedArrayList);
    }

    public static long sumArrayListSequential(ArrayList<Integer> arrayList) {
        return arrayList.stream()
                .mapToLong(i -> i)
                .sum();
    }

    public static long sumArrayListParallel(ArrayList<Integer> arrayList) {
        return arrayList.stream()
                .mapToLong(i -> i)
                .parallel()
                .sum();
    }

    public static long sumArraySequential(int[] array) {
        return Arrays.stream(array)
                .mapToLong(i -> i)
                .sum();
    }

    public static long sumArrayParallel(int[] array) {
        return Arrays.stream(array)
                .mapToLong(i -> i)
                .parallel()
                .sum();
    }

    public static long sumSortedArrayListSequential(ArrayList<Integer> sortedArrayList) {
        return sortedArrayList.stream()
                .mapToLong(i -> i)
                .sum();
    }

    public static long sumSortedArrayListParallel(ArrayList<Integer> sortedArrayList) {
        return sortedArrayList.stream()
                .mapToLong(i -> i)
                .parallel()
                .sum();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + Stream_05_Locality_Benchmark.class.getSimpleName() + ".*")
                .build();

        new Runner(opt).run();
    }
}
