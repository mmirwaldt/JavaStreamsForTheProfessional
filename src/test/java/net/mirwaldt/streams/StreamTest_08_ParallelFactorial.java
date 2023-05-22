package net.mirwaldt.streams;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
public class StreamTest_08_ParallelFactorial {
    @Test
    void test() {
        Stream_08_ParallelFactorial_Benchmark benchmark = new Stream_08_ParallelFactorial_Benchmark();
        assertEquals(benchmark.parallelStream(), benchmark.forkJoinPool());
        assertEquals(benchmark.parallelStreamKaratsubaTomCook(), benchmark.forkJoinPoolKaratsubaTomCook());
        assertEquals(benchmark.parallelStream(), benchmark.parallelStreamKaratsubaTomCook());
        assertEquals(benchmark.forkJoinPool(), benchmark.forkJoinPoolKaratsubaTomCook());
    }
}
