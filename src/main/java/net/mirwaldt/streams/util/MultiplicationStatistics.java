package net.mirwaldt.streams.util;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import static java.math.BigInteger.ONE;
import static net.mirwaldt.streams.util.Factorial.parallelStream;
import static net.mirwaldt.streams.util.FactorialTask.forkJoinPool;
import static net.mirwaldt.streams.util.KaratsubaTomCook.KARATSUBA_THRESHOLD_IN_BITS;
import static net.mirwaldt.streams.util.KaratsubaTomCook.TOM_COOK_THRESHOLD_IN_BITS;

/*
Output:
Multiplications:
	Possible at most:
		Total multiplications: 99999
		Karatsuba multiplications: 395
		TomCook multiplications: 196
	Factorial.parallelStream:
		Total multiplications: 99998
		Karatsuba multiplications: 0
		TomCook multiplications: 63
	FactorialTask.forkJoinPool:
		Total multiplications: 99998
		Karatsuba multiplications: 362
		TomCook multiplications: 126
	KaratsubaTomCook.parallelStream:
		Total multiplications: 99998
		Karatsuba multiplications: 394
		TomCook multiplications: 180
	KaratsubaTomCookFactorialTask.forkJoinPool:
		Total multiplications: 99972
		Karatsuba multiplications: 384
		TomCook multiplications: 188
 */
public class MultiplicationStatistics {

    public static final int N = 100_000;

    // We want to find out how many karatsuba and tom-cook multiplications are possible and happen in all solutions
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MultiplicationsCounter watcher = new MultiplicationsCounter();
        BigInteger result = parallelStream(N, watcher::multiplyAndWatch);

        System.out.println("Multiplications:");
        System.out.println("\tPossible at most:");
        System.out.println("\t\tTotal multiplications: " + (N - 1));
        int maxTomCookMultiplications = result.bitLength() / TOM_COOK_THRESHOLD_IN_BITS - 1;
        int maxKaratsubaMultiplications = (result.bitLength() / KARATSUBA_THRESHOLD_IN_BITS) - 1 - maxTomCookMultiplications;
        System.out.println("\t\tKaratsuba multiplications: " + maxKaratsubaMultiplications);
        System.out.println("\t\tTomCook multiplications: " + maxTomCookMultiplications); // -1 because we count pairs

        System.out.println("\tFactorial.parallelStream:");
        System.out.println("\t\tTotal multiplications: " + watcher.totalCount());
        System.out.println("\t\tKaratsuba multiplications: " + watcher.karatsubaCount());
        System.out.println("\t\tTomCook multiplications: " + watcher.tomCookCount());

        watcher = new MultiplicationsCounter();
        forkJoinPool(N, watcher::multiplyAndWatch);
        System.out.println("\tFactorialTask.forkJoinPool:");
        System.out.println("\t\tTotal multiplications: " + watcher.totalCount());
        System.out.println("\t\tKaratsuba multiplications: " + watcher.karatsubaCount());
        System.out.println("\t\tTomCook multiplications: " + watcher.tomCookCount());

        watcher = new MultiplicationsCounter();
        KaratsubaTomCook.parallelStream(N, watcher::multiplyAndWatch);
        System.out.println("\tKaratsubaTomCook.parallelStream:");
        System.out.println("\t\tTotal multiplications: " + watcher.totalCount());
        System.out.println("\t\tKaratsuba multiplications: " + watcher.karatsubaCount());
        System.out.println("\t\tTomCook multiplications: " + watcher.tomCookCount());

        watcher = new MultiplicationsCounter();
        KaratsubaTomCookFactorialTask.forkJoinPool(N, 7000, watcher::multiplyAndWatch);
        System.out.println("\tKaratsubaTomCookFactorialTask.forkJoinPool:");
        System.out.println("\t\tTotal multiplications: " + watcher.totalCount());
        System.out.println("\t\tKaratsuba multiplications: " + watcher.karatsubaCount());
        System.out.println("\t\tTomCook multiplications: " + watcher.tomCookCount());
    }

    static class MultiplicationsCounter {
        private final AtomicLong totalCounter = new AtomicLong();
        private final AtomicLong karatsubaCounter = new AtomicLong();
        private final AtomicLong tomCookCounter = new AtomicLong();

        public BigInteger multiplyAndWatch(BigInteger left, BigInteger right) {
            if(!left.equals(ONE) && !right.equals(ONE)) {
                if (TOM_COOK_THRESHOLD_IN_BITS <= left.bitLength()
                        && TOM_COOK_THRESHOLD_IN_BITS <= right.bitLength()) {
                    tomCookCounter.incrementAndGet();
                } else if (KARATSUBA_THRESHOLD_IN_BITS <= left.bitLength()
                        && KARATSUBA_THRESHOLD_IN_BITS <= right.bitLength()) {
                    karatsubaCounter.incrementAndGet();
                }
                totalCounter.incrementAndGet();
            }
            return left.multiply(right);
        }

        public long totalCount() {
            return totalCounter.get();
        }

        public long karatsubaCount() {
            return karatsubaCounter.get();
        }

        public long tomCookCount() {
            return tomCookCounter.get();
        }
    }
}
