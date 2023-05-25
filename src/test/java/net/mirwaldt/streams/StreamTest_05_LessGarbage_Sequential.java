package net.mirwaldt.streams;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
public class StreamTest_05_LessGarbage_Sequential {
    @Test
    void test() {
        Stream_05_LessGarbage_Sequential_Benchmark benchmark = new Stream_05_LessGarbage_Sequential_Benchmark();
        assertEquals(benchmark.reduceWithStringBuilders(), benchmark.reduceWithStrings());
    }
}
