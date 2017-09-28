package com.shreyash.main.decode;

import com.shreyash.main.Main;
import com.shreyash.main.helpers.BitInputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static com.shreyash.main.helpers.Common.logCeil;

public class SimpleDecompressor implements IDecompressor {


    private static String IOEXCEPTION_MESSAGE = "There was an error while reading the file to decode";

    private BitInputStream inputStream;


    public void decode(String compressedFileName) throws IOException {

        try {
            inputStream = new BitInputStream(compressedFileName);
            int totalSymbols = inputStream.read(Main.INTEGER_LENGTH);
            System.out.println("Total symbols to read " + totalSymbols);
            /* Find out how many unique elements there are */
            HashMap<Integer, Byte> dictionary = this.getDictionary(inputStream);
            int uniqueElements = dictionary.size();
            int bitsPerSymbol = logCeil(uniqueElements);
            /* Using the unique elements to compute how many
             * bits of data will be needed
             */
            mapCodeToSymbols(dictionary, inputStream, bitsPerSymbol, totalSymbols,
                    getReconstructedFileName(compressedFileName));
        }
        catch(IOException e) {
            e.printStackTrace();
            throw new IOException(IOEXCEPTION_MESSAGE);
        }

    }

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

    public void mapCodeToSymbols(HashMap<Integer, Byte> dictionary, BitInputStream inputStream, int bitsPerSymbol,
                                 int totalNumSymbols, String outputFileName) throws IOException {

        byte[] outputData = new byte[totalNumSymbols];
        int numRead = 0;
        while(numRead < totalNumSymbols) {
            int code = inputStream.read(bitsPerSymbol);
            outputData[numRead++] = dictionary.get(code);
            System.out.println("Output data: " + (numRead - 1) + " is " + outputData[numRead - 1]);
        }

        Path path = Paths.get(outputFileName);
        Files.write(path, outputData);

    }

    public String getReconstructedFileName(String compressedFileName) {

        String[] fileParts = compressedFileName.split("\\.(?=[^\\.]+$)");
        String[] filePrefixParts = fileParts[0].split("-");
        String filePrefix = filePrefixParts[0];
        String outputFile = filePrefix + "-reconstructed." + fileParts[1];
        System.out.println(outputFile);
        return outputFile;
    }
}
