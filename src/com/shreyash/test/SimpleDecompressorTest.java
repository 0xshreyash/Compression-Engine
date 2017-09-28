package com.shreyash.test;

import com.shreyash.main.decode.SimpleDecompressor;
import org.junit.Test;

import java.io.IOException;

public class SimpleDecompressorTest {

    @Test
    public void testRead() throws IOException {
        SimpleDecompressor decompressor = new SimpleDecompressor();
        decompressor.decode("tester-compressed.txt");
    }
}
