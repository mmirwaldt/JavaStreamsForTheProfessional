package net.mirwaldt.streams;

import net.mirwaldt.streams.util.Factorial;
import net.mirwaldt.streams.util.FactorialTask;
import net.mirwaldt.streams.util.KaratsubaTomCook;
import net.mirwaldt.streams.util.KaratsubaTomCookFactorialTask;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("DuplicatedCode")
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Stream_08_ParallelFactorial_Benchmark {
    /*
        The solution with the ForkJoinPool is much faster than the solution with the parallel stream by default:

        Benchmark                                                               Mode  Cnt   Score   Error  Units
        Stream_08_ParallelFactorial_Benchmark.forkJoinPool                      avgt   25   16.564 ± 0.022  ms/op
        Stream_08_ParallelFactorial_Benchmark.forkJoinPoolKaratsubaTomCook      avgt   25   23.108 ± 0.037  ms/op
        Stream_08_ParallelFactorial_Benchmark.parallelStream                    avgt   25   39.100 ± 0.026  ms/op
        Stream_08_ParallelFactorial_Benchmark.parallelStreamKaratsubaTomCook    avgt   25   18.728 ± 0.060  ms/op

        However, these results also show the solution with the parallel stream can be optimized for more Karatsuba and
        Tom-Cook multiplications and gets faster but the solution with the ForkJoinPool suffers from that optimization.
     */
    public int N = 100_000;

    @Benchmark
    public BigInteger parallelStream() {
        return Factorial.parallelStream(N, BigInteger::parallelMultiply);
    }

    @Benchmark
    public BigInteger forkJoinPool() {
        return FactorialTask.forkJoinPool(N, BigInteger::parallelMultiply);
    }

    @Benchmark
    public BigInteger parallelStreamKaratsubaTomCook() {
        return KaratsubaTomCook.parallelStream(N, BigInteger::parallelMultiply);
    }

    @Benchmark
    public BigInteger forkJoinPoolKaratsubaTomCook() {
        return KaratsubaTomCookFactorialTask.forkJoinPool(N, 7000, BigInteger::parallelMultiply);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Stream_08_ParallelFactorial_Benchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
