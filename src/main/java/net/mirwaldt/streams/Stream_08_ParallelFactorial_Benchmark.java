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
Benchmark                                                                                 Mode  Cnt   Score   Error  Units
Benchmark_11_ParallelFactorial.factorialParallelInForkJoinPoolParallelMultiply            avgt   25  16.553 ± 0.039  ms/op
Benchmark_11_ParallelFactorial.factorialParallelInForkJoinPoolSequentialMultiply          avgt   25  29.527 ± 0.371  ms/op
Benchmark_11_ParallelFactorial.factorialParallelStreamParallelMultiply                    avgt   25  39.061 ± 0.045  ms/op
Benchmark_11_ParallelFactorial.factorialParallelStreamSequentialMultiply                  avgt   25  53.479 ± 0.228  ms/op
Benchmark_11_ParallelFactorial.tomCookKaratsubaFactorialForkJoinPoolParallelMultiply      avgt   25  23.045 ± 0.021  ms/op
Benchmark_11_ParallelFactorial.tomCookKaratsubaFactorialForkJoinPoolSequentialMultiply    avgt   25  36.272 ± 0.310  ms/op
Benchmark_11_ParallelFactorial.tomCookKaratsubaFactorialParallelStreamParallelMultiply    avgt   25  18.622 ± 0.037  ms/op
Benchmark_11_ParallelFactorial.tomCookKaratsubaFactorialParallelStreamSequentialMultiply  avgt   25  32.891 ± 0.123  ms/op

Saturation can be measured in bash by:
while true; do uptime | cut -d ',' -f 2,4 ; sleep 1; done
Example output:
12:24,  load average: 2.66

Max saturation of CPU I have observed: 7.79 with factorialParallelInForkJoinPoolParallelMultiply
=> Good because FJP has only got 7 threads => max. 7.00
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
