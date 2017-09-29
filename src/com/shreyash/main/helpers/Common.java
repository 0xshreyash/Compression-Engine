/**
 * @Author: Shreyash Patodia
 */
package com.shreyash.main.helpers;

/**
 * Class containing the common methods used by multiple classes in this application.
 */
public class Common {

    /**
     * This method takes the number of of symbols for a given file and outputs
     * the number of bits we need to encode each letter in the file.
     * <p>
     * I do a +1 because, we also need to accommodate for not being able to
     * use a null byte here as an encoding i.e. 0 cannot be an encoding because
     * then it gives issue with ascii.
     *
     * @param numSymbols the number of unique symbols in the file
     * @return the number of bits needed to encode the symbols
     */
    public static int logCeil(int numSymbols) {
        return (int) (Math.ceil(Math.log(numSymbols + 1) / Math.log(2)));
    }
}
