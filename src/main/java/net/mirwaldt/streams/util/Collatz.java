package net.mirwaldt.streams.util;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicIntegerArray;

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

    public static int collatzSteps(int n) {
        int i = n;
        int result = 0;
        while (1 < i) {
            i = collatz(i);
            result++;
        }
        return result;
    }

    private static int collatz(int i) {
        if (i % 2 == 0) {
            return i / 2;
        } else {
            return 3 * i + 1;
        }
    }

    public static int cachedCollatzMaxSteps(int n, int maxSteps, ConcurrentHashMap<Integer, Integer> map) {
        int steps;
        int i = n;
        steps = 0;
        while (1 < i && steps < maxSteps) {
            i = collatz(i);
            int cached;
            if (4 < i && i < map.size() && 0 < (cached = map.getOrDefault(i - 4, 0))) {
                steps += cached;
                return steps;
            }
            steps++;
        }
        if (4 < n && n < map.size()) {
            map.put(n - 4, steps);
        }
        return steps;
    }

    public static int cachedCollatzMaxSteps(int n, int maxSteps, AtomicIntegerArray array) {
        int steps;
        int i = n;
        steps = 0;
        while (1 < i && steps < maxSteps) {
            i = collatz(i);
            int cached;
            if (4 < i && i < array.length() && 0 < (cached = array.get(i - 4))) {
                steps += cached;
                return steps;
            }
            steps++;
        }
        if (4 < n && n < array.length()) {
            array.set(n - 4, steps);
        }
        return steps;
    }

    public static void main(String[] args) {
        int[] results = new int[1_000_000];
        for (int start = 5; start < results.length; start++) {
//            if(start % 1000 == 0) {
//                System.out.println(start);
//            }
            int next = start;
            results[next - 1]++;
            while (1 < next) {
                next = collatz(next);
                if (4 < next && next <= results.length) {
                    results[next - 1]++;
                }
            }
        }
//        System.out.println(Arrays.stream(results).filter(i -> 1 < i).boxed().count());
        System.out.println(Arrays.stream(results).filter(i -> 10000 < i).boxed().count());
    }
}
