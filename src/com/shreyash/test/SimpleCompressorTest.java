package com.shreyash.test;

import com.shreyash.main.encode.SimpleCompressor;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.testng.annotations.Test;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.AssertJUnit.assertEquals;

public class SimpleCompressorTest {

    @Test
    public void simpleTest() throws IOException{

        Path path = Paths.get("tester.txt");
        byte[] dataStream = Files.readAllBytes(path);
        SimpleCompressor simpleCompressor = new SimpleCompressor();

        String fileName = simpleCompressor.getCompressedFileName("tester.txt");
        assertEquals("tester-compressed.txt", fileName);
        assertEquals(1, simpleCompressor.getNumUniqueSymbols(dataStream));
        assertEquals(1, simpleCompressor.logCeil(simpleCompressor.getNumUniqueSymbols(dataStream)));

        simpleCompressor.encode("tester.txt");

        Path pathCompressed = Paths.get("tester-compressed.txt");
        byte[] dataStreamCompressed = Files.readAllBytes(pathCompressed);
        for(int i = 0; i < dataStreamCompressed.length; i++) {
            System.out.println(dataStreamCompressed[i]);
        }
    }


}
