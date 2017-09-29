/**
 * @Author: Shreyash Patodia
 */
package com.shreyash.main.decode;

import com.shreyash.main.Main;
import com.shreyash.main.helpers.BitInputStream;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static com.shreyash.main.helpers.Common.logCeil;

/**
 * This is a simple decompressor that takes a compressed file
 * and reconstructs it, in order to produce a file with the same
 * same name as the original (uncompressed) file with a suffix
 * reconstructed. So if the original file was <filename>.txt then the
 * compressed file is <filename>-compressed.txt. The reconstructed
 * file would be <filename>-reconstructed.txt
 */
public class SimpleDecompressor implements IDecompressor {


    /**
     * Error message
     */
    private static String IOEXCEPTION_MESSAGE = "There was an error while reading the file to decode";

    /** The stream to be reading the compressed file from */
    private BitInputStream inputStream;

    public SimpleDecompressor() {
        this.inputStream = null;
    }

    /**
     * This method takes the file name corresponding to the compressed file and creates a new file
     * which contains the reconstructed file.
     * @param compressedFileName the name of the compressed file
     * @throws IOException
     */
    public String decode(String compressedFileName) throws IOException {
        String reconstructedFile = null;
        try {

            inputStream = new BitInputStream(compressedFileName);

            /*
             * First thing the compressed file has, is the number of elements in the
             * original file.
             */
            int totalSymbols = inputStream.read(Main.INTEGER_LENGTH);
            //System.out.println("Total symbols to read " + totalSymbols);

            /*
             * Find out how many unique elements there are as that deterimes how many bits
             * each code was
             */
            HashMap<Integer, Byte> dictionary = this.getDictionary(inputStream);
            int uniqueElements = dictionary.size();
            int bitsPerSymbol = logCeil(uniqueElements);
            reconstructedFile = getReconstructedFileName(compressedFileName);
            /* Write the actual symbols to file */
            mapCodeToSymbols(dictionary, inputStream, bitsPerSymbol, totalSymbols,
                    reconstructedFile);
        }
        catch(IOException e) {
            e.printStackTrace();
            throw new IOException(IOEXCEPTION_MESSAGE);
        }
        return reconstructedFile;

    }

    /**
     * This methods reads the number of unique elements and the dictionary from the
     * compressed file and allows us to reconstruct the original file.
     * @param inputStream the stream where the file is open and we can read bits from
     * @return the dictionary mapping the codes to the actual bytes
     */
    public HashMap<Integer, Byte> getDictionary(BitInputStream inputStream) {
        int numUniqueElements = inputStream.read(Main.BYTE_LENGTH);
        //System.out.println("Number of unique elements: " + numUniqueElements);
        HashMap<Integer, Byte> dictionary = new HashMap<>();
        int bitsPerSymbol = logCeil(numUniqueElements);
        int symbolsRead = 0;
        while(symbolsRead < numUniqueElements) {
            byte actualSymbolCode = (byte)inputStream.read(Main.BYTE_LENGTH);
            int code =  inputStream.read(bitsPerSymbol);
            dictionary.put(code, actualSymbolCode);
            //System.out.println("The code for the symbol: " + actualSymbolCode + " was " + code);
            symbolsRead++;
        }
        return dictionary;

    }

    /**
     * Reads the code from the compressed file and write the corresponding symbol to the reconstructed file
     * @param dictionary the mapping from an encoding to the symbols
     * @param inputStream the stream from which we are reading the symbols from
     * @param bitsPerSymbol the number of bits each symbol corresponds to
     * @param totalNumSymbols the total number of symbols to be read
     * @param outputFileName the name of the file with the reconstructed name.
     * @throws IOException
     */
    public void mapCodeToSymbols(HashMap<Integer, Byte> dictionary, BitInputStream inputStream, int bitsPerSymbol,
                                 int totalNumSymbols, String outputFileName) throws IOException {

        byte[] outputData = new byte[totalNumSymbols];
        int numRead = 0;
        while(numRead < totalNumSymbols) {
            int code = inputStream.read(bitsPerSymbol);
            outputData[numRead++] = dictionary.get(code);
            //System.out.println("Output data: " + (numRead - 1) + " is " + outputData[numRead - 1]);
        }
        //outputData[numRead] = LINE_FEED_ASCII;
        Path path = Paths.get(outputFileName);
        Files.write(path, outputData);

    }

    /**
     * Constructs the file name of the reconstructed file from the
     * @param compressedFileName the file name of the compressed file
     * @return the file name of the reconstructed file
     */
    public String getReconstructedFileName(String compressedFileName) {

        String[] fileParts = compressedFileName.split("\\.(?=[^\\.]+$)");
        String[] filePrefixParts = fileParts[0].split("-");
        String filePrefix = filePrefixParts[0];
        String outputFile = filePrefix + "-reconstructed." + fileParts[1];
        //System.out.println(outputFile);
        return outputFile;
    }
}
