/**
 * @Author: Shreyash Patodia
 */
package code;

import code.decode.SimpleDecompressor;
import code.encode.SimpleCompressor;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Tests written for the decompressor.
 */
public class CompressionDecompressionTest {

    @Test
    public void testCompressionAndDecompression() throws IOException {
        SimpleCompressor compressor = new SimpleCompressor();
        SimpleDecompressor decompressor = new SimpleDecompressor();

        compressor.encode("test_files/tester.txt");

        decompressor.decode("test_files/tester-compressed.txt");
        Path original = Paths.get("test_files/tester.txt");
        byte[] f1 = Files.readAllBytes(original);
        //compressor.encode("test_files/tester.txt");
        Path reconstructed = Paths.get("test_files/tester-reconstructed.txt");

        byte[] f2 = Files.readAllBytes(reconstructed);
        assertEquals(true, Arrays.equals(f1, f2));
    }

    @Test
    public void testCompressionAndDecompressionBig() throws IOException {
        SimpleCompressor compressor = new SimpleCompressor();
        SimpleDecompressor decompressor = new SimpleDecompressor();

        compressor.encode("test_files/big.txt");

        decompressor.decode("test_files/big-compressed.txt");
        Path original = Paths.get("test_files/big.txt");
        byte[] f1 = Files.readAllBytes(original);
        //compressor.encode("test_files/big.txt");
        Path reconstructed = Paths.get("test_files/big-reconstructed.txt");

        byte[] f2 = Files.readAllBytes(reconstructed);
        assertEquals(true, Arrays.equals(f1, f2));
    }

    @Test
    public void testCompressionAndDecompressionShort() throws IOException {
        SimpleCompressor compressor = new SimpleCompressor();
        SimpleDecompressor decompressor = new SimpleDecompressor();

        compressor.encode("test_files/short.txt");

        decompressor.decode("test_files/short-compressed.txt");
        Path original = Paths.get("test_files/short.txt");
        byte[] f1 = Files.readAllBytes(original);
        //compressor.encode("test_files/short.txt");
        Path reconstructed = Paths.get("test_files/short-reconstructed.txt");

        byte[] f2 = Files.readAllBytes(reconstructed);
        assertEquals(true, Arrays.equals(f1, f2));
    }


    @Test
    public void testCompressionAndDecompressionSix() throws IOException {
        SimpleCompressor compressor = new SimpleCompressor();
        SimpleDecompressor decompressor = new SimpleDecompressor();

        compressor.encode("test_files/test6.txt");

        decompressor.decode("test_files/test6-compressed.txt");
        Path original = Paths.get("test_files/test6.txt");
        byte[] f1 = Files.readAllBytes(original);
        //compressor.encode("test_files/short.txt");
        Path reconstructed = Paths.get("test_files/test6-reconstructed.txt");

        byte[] f2 = Files.readAllBytes(reconstructed);
        assertEquals(true, Arrays.equals(f1, f2));
    }


    @Test
    public void testCompressionAndDecompressionSeven() throws IOException {
        SimpleCompressor compressor = new SimpleCompressor();
        SimpleDecompressor decompressor = new SimpleDecompressor();

        compressor.encode("test_files/test7.txt");

        decompressor.decode("test_files/test7-compressed.txt");
        Path original = Paths.get("test_files/test7.txt");
        byte[] f1 = Files.readAllBytes(original);
        //compressor.encode("test_files/short.txt");
        Path reconstructed = Paths.get("test_files/test7-reconstructed.txt");

        byte[] f2 = Files.readAllBytes(reconstructed);
        assertEquals(true, Arrays.equals(f1, f2));
    }

}
