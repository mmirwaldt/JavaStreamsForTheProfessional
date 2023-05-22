package net.mirwaldt.streams;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
public class StreamTest_02_RangeClosed_LargerSteps {
    @Test
    void test_main() {
        PrintStream outStream = System.out;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
        Stream_02_RangeClosed_LargerSteps.main(new String[0]);
        System.setOut(outStream);
        assertEquals("22", os.toString().trim());
    }
}
