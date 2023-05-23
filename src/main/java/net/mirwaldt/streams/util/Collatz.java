package net.mirwaldt.streams.util;

public class Collatz {
    public static int collatzMaxSteps(int n, int maxSteps) {
        int i = n;
        int steps = 0;
        while (1 < i && steps < maxSteps) {
            i = collatz(i);
            steps++;
        }
        return steps;
    }

    private static int collatz(int i) {
        if (i % 2 == 0) {
            return i / 2;
        } else {
            return 3 * i + 1;
        }
    }
}
