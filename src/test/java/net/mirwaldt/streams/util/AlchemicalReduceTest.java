package net.mirwaldt.streams.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static net.mirwaldt.streams.util.AlchemicalReduce.reduce;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlchemicalReduceTest {
    private static Stream<Arguments> alchemicalReducer() {
        return Stream.of(
                Arguments.of((UnaryOperator<String>) (String input) -> input.chars()
                        .mapToObj(c -> (char) c)
                        .reduce("", AlchemicalReduce::reduce, AlchemicalReduce::combine)),
                Arguments.of((UnaryOperator<String>) (String input) -> input.chars()
                        .collect(StringBuilder::new, (result, i) -> reduce(result, (char) i), AlchemicalReduce::combine)
                        .toString()));
    }

    @ParameterizedTest
    @MethodSource("alchemicalReducer")
    void test_noElimination(UnaryOperator<String> alchemicalReducer) {
        assertEquals("", alchemicalReducer.apply(""));
        assertEquals("a", alchemicalReducer.apply("a"));
        assertEquals("AA", alchemicalReducer.apply("AA"));
        assertEquals("aB", alchemicalReducer.apply("aB"));
    }

    @ParameterizedTest
    @MethodSource("alchemicalReducer")
    void test_oneElimination(UnaryOperator<String> alchemicalReducer) {
        assertEquals("", alchemicalReducer.apply("aA"));
        assertEquals("", alchemicalReducer.apply("Aa"));
        assertEquals("a", alchemicalReducer.apply("aaA"));
        assertEquals("A", alchemicalReducer.apply("AaA"));
        assertEquals("b", alchemicalReducer.apply("baA"));
        assertEquals("B", alchemicalReducer.apply("AaB"));
    }

    @ParameterizedTest
    @MethodSource("alchemicalReducer")
    void test_twoEliminations_nonRecursive(UnaryOperator<String> alchemicalReducer) {
        assertEquals("", alchemicalReducer.apply("aAAa"));
        assertEquals("", alchemicalReducer.apply("AabB"));
        assertEquals("a", alchemicalReducer.apply("aAabB"));
        assertEquals("A", alchemicalReducer.apply("AaAbB"));
        assertEquals("b", alchemicalReducer.apply("bBaAb"));
    }

    @ParameterizedTest
    @MethodSource("alchemicalReducer")
    void test_threeEliminations_nonRecursive(UnaryOperator<String> alchemicalReducer) {
        assertEquals("", alchemicalReducer.apply("aAAaAa"));
        assertEquals("", alchemicalReducer.apply("AabBAa"));
        assertEquals("aC", alchemicalReducer.apply("aAabBCcC"));
        assertEquals("AC", alchemicalReducer.apply("AaAbBbBC"));
    }

    @ParameterizedTest
    @MethodSource("alchemicalReducer")
    void test_twoEliminations_oneRecursion(UnaryOperator<String> alchemicalReducer) {
        assertEquals("", alchemicalReducer.apply("abBA"));
        assertEquals("B", alchemicalReducer.apply("BabBA"));
        assertEquals("A", alchemicalReducer.apply("abBAA"));
        assertEquals("BA", alchemicalReducer.apply("BabBAaAA"));
    }

    @ParameterizedTest
    @MethodSource("alchemicalReducer")
    void test_twoEliminations_twoRecursions(UnaryOperator<String> alchemicalReducer) {
        assertEquals("", alchemicalReducer.apply("CabBAc"));
        assertEquals("B", alchemicalReducer.apply("BCabBAc"));
        assertEquals("A", alchemicalReducer.apply("CabBAcA"));
        assertEquals("BA", alchemicalReducer.apply("BCabBAaAcA"));
    }

    @ParameterizedTest
    @MethodSource("alchemicalReducer")
    void test_complexExample(UnaryOperator<String> alchemicalReducer) {
        // Copied from puzzle from advent of code
        assertEquals("dabCBAcaDA", alchemicalReducer.apply("dabAcCaCBAcCcaDA"));
    }
}
