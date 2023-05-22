package net.mirwaldt.streams;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
public class StreamTest_07_LessGarbage_Parallel {
    @Test
    void test() {
        Stream_07_LessGarbage_Parallel_Benchmark benchmark = new Stream_07_LessGarbage_Parallel_Benchmark();
        assertEquals(benchmark.reduceWithStringBuildersSequential(), benchmark.reduceWithStringBuildersParallel());
    }
}
