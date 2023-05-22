package net.mirwaldt.streams;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
public class StreamTest_01_RangeClosed {
    @Test
    void test() {
        Stream_01_RangeClosed_Benchmark benchmark = new Stream_01_RangeClosed_Benchmark();
        assertEquals(benchmark.filterLimitParallelStream(), benchmark.rangeClosedParallelStream());
    }
}
