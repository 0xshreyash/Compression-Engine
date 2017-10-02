/**
 * @Author: Shreyash Patodia
 */
package code.encode;

import java.io.IOException;

/**
 * Interface that defines what a compressor should do, i.e.
 * provide a method to java.encode a file with a ceetain name.
 */
public interface ICompressor {
    String encode(String fileName) throws IOException;
}
