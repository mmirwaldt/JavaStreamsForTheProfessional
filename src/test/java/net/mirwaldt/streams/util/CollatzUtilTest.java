package net.mirwaldt.streams.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollatzUtilTest {
    @Test
    void test() {
        assertEquals(0, Collatz.collatzMaxSteps(1, Integer.MAX_VALUE));
        assertEquals(1, Collatz.collatzMaxSteps(2, Integer.MAX_VALUE));
        assertEquals(2, Collatz.collatzMaxSteps(4, Integer.MAX_VALUE));

        // 5, 3*5+1=16, 16/2=8, 8/2=4, 4/2=2, 2/2=1
        assertEquals(5, Collatz.collatzMaxSteps(5, Integer.MAX_VALUE));

        // 6, 6/2=3, 3*3+1=10, 10/2=5, CollatzUtil.follow(5)
        assertEquals(8, Collatz.collatzMaxSteps(6, Integer.MAX_VALUE));

        // 7*3+1=22, 22/2=11, 3*11+1=34, 34/2=17, 17*3+1=52, 52/2=26, 26/2=13,
        // 13*3+1=40. 40/2=20, 20/2=10, 10/2=5, CollatzUtil.follow(5)
        assertEquals(16, Collatz.collatzMaxSteps(7, Integer.MAX_VALUE));
    }
}
