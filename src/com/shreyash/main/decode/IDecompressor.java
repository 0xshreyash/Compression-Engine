/**
 * @Author: Shreyash Patodia
 */
package com.shreyash.main.decode;

import java.io.IOException;

/**
 * This interface defines what a decompressor should do, i.e.
 * have a decoding behaviour.
 */
public interface IDecompressor {

    String decode(String fileName) throws IOException;
}
