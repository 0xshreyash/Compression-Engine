/**
 * @Author: Shreyash Patodia
 */
package com.shreyash.main.encode;

import java.io.IOException;

/**
 * Interface that defines what a compressor should do, i.e.
 * provide a method to encode a file with a ceetain name.
 */
public interface ICompressor {
    String encode(String fileName) throws IOException;
}
