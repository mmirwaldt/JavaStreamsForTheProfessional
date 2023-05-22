package net.mirwaldt.streams;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
public class StreamTest_03_RangeClosed_DescendingOrder {
    @Test
    void test_main() {
        PrintStream outStream = System.out;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
        Stream_03_RangeClosed_DescendingOrder.main(new String[0]);
        System.setOut(outStream);
        assertEquals("3030267", os.toString().trim());
    }
}
