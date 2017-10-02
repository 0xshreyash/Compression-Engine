/**
 * @Author: Shreyash Patodia
 */
package code;

import code.decode.SimpleDecompressor;
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
        SimpleDecompressor decompressor = new SimpleDecompressor();
        decompressor.decode("test_files/tester-compressed.txt");
        Path original = Paths.get("test_files/tester.txt");
        Path reconstructed = Paths.get("test_files/tester-reconstructed.txt");
        byte[] f1 = Files.readAllBytes(original);
        byte[] f2 = Files.readAllBytes(reconstructed);
        assertEquals(true, Arrays.equals(f1, f2));
    }

}
