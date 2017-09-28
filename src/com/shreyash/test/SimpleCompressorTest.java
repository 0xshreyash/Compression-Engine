package com.shreyash.test;

import com.shreyash.main.encode.SimpleCompressor;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.shreyash.main.helpers.Common.logCeil;
import static org.testng.AssertJUnit.assertEquals;

public class SimpleCompressorTest {

    @Test
    public void simpleTest() throws IOException {

        Path path = Paths.get("tester.txt");
        byte[] dataStream = Files.readAllBytes(path);
        SimpleCompressor simpleCompressor = new SimpleCompressor();

        String fileName = simpleCompressor.getCompressedFileName("tester.txt");
        assertEquals("tester-compressed.txt", fileName);
        assertEquals(3, simpleCompressor.getDictionary(dataStream).size());
        assertEquals(2, logCeil(simpleCompressor.getDictionary(dataStream).size()));

        simpleCompressor.encode("tester.txt");

        Path pathCompressed = Paths.get("tester-compressed.txt");
        byte[] dataStreamCompressed = Files.readAllBytes(pathCompressed);
        for(int i = 0; i < dataStreamCompressed.length; i++) {
            System.out.println("dataStreamCompressed " + i + " is " + dataStreamCompressed[i]);
        }
    }


}
