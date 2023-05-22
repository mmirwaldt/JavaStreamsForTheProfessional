package net.mirwaldt.streams.util;

import java.math.BigInteger;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

import static java.math.BigInteger.ONE;

public class KaratsubaTomCook {
    public static final int KARATSUBA_THRESHOLD_IN_BITS = 2560;
    public static final int TOM_COOK_THRESHOLD_IN_BITS = 7680;

    public static void accumulate(BigInteger[] result, BigInteger i, BinaryOperator<BigInteger> multiply) {
        result[2] = multiply.apply(result[2], i);
        if (KARATSUBA_THRESHOLD_IN_BITS <= result[2].bitLength()) {
            result[1] = multiply.apply(result[1], result[2]);
            result[2] = ONE;
        }
        if (TOM_COOK_THRESHOLD_IN_BITS <= result[1].bitLength()) {
            result[0] = multiply.apply(result[0], result[1]);
            result[1] = ONE;
        }
    }

    public static void combine(BigInteger[] left, BigInteger[] right, BinaryOperator<BigInteger> multiply) {
        accumulate(left, right[2], multiply);
        accumulate(left, right[1], multiply);
        accumulate(left, right[0], multiply);
    }

    public static BigInteger parallelStream(int n, BinaryOperator<BigInteger> multiply) {
        BigInteger[] results = IntStream.rangeClosed(2, n)
                .mapToObj(BigInteger::valueOf)
                .parallel()
                .collect(() -> new BigInteger[]{ONE, ONE, ONE},
                        (array, i) -> accumulate(array, i, multiply),
                        (left, right) -> combine(left, right, multiply)
                );
        return multiply.apply(results[0], multiply.apply(results[1], results[2]));
    }
}
