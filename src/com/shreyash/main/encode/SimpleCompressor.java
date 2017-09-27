package com.shreyash.main.encode;

import com.shreyash.main.Main;
import com.shreyash.main.helpers.BitOutputStream;
import com.shreyash.main.helpers.BitSequence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This is a class that is effective when encoding files of
 * data, where the symbols will be in something close to uniform
 * distribution.
 * <p>
 * The method works by taking an inital pass through the array
 * and finding out how many unique symbols there are in the file,
 * it then computes how many bits of information will be needed
 * to code each symbol. We take this to be ceil(log(no. of unique
 * symbols)) because on average the no. of bits required to encode
 * each symbol
 */
public class SimpleCompressor implements ICompressor {

    private static String CANNOT_COMPRESS_MESSAGE = "Cannot compress the file any further" +
            " using SimpleCompressor. Sorry!";
    private static String IOEXCEPTION_MESSAGE = "There was an error while reading the file";

    private static String FILE_NOT_FOUND_MESSAGE = "The file {0} was not found";

    /**
     * This method is the method that is called when we want to
     * encode something and output it as file. The output of this
     * method is a file with the same name as that of the input file
     * but with a the suffix -compressed to the name. The extension of the
     * file is the same as the original file.
     *
     * @param fileName
     * @throws IOException
     */
    @Override
    public void encode(String fileName) throws IOException {
        try {
            /* Reading in the data from the file */
            Path path = Paths.get(fileName);
            byte[] dataStream = Files.readAllBytes(path);

            /* Find out how many unique elements there are */
            int uniqueElements = getNumUniqueSymbols(dataStream);

            String compressedFileName = getCompressedFileName(fileName);

            /* Using the unique elements to compute how many
             * bits of data will be needed
             */
            int bitsPerSymbol = logCeil(uniqueElements);

            /*
             * Say that the file cannot be compressed
             */
            if (bitsPerSymbol == Main.BYTE_LEN) {
                Main.printLine(CANNOT_COMPRESS_MESSAGE);
                return;
            } else {
                HashMap<Byte, Integer> dictionary = mapSymbolsToCode(compressedFileName, dataStream, bitsPerSymbol);

            }
        }
        /* Exception handling */ catch (IOException e) {
            throw new IOException(IOEXCEPTION_MESSAGE);
        }
    }

    public int getNumUniqueSymbols(byte[] dataStream) {
        /* Counting the number of unique bytes */
        Set<Byte> uniqueBytes = new HashSet<>();
        for (byte b : dataStream) {
            uniqueBytes.add(b);
        }
        int uniqueElements = uniqueBytes.size() - 1;
        return uniqueElements;
    }

    public HashMap<Byte, Integer> mapSymbolsToCode(String newFileName, byte[] dataStream, int bitsPerSymbol)
            throws IOException {

        File file = new File(newFileName);
        //System.out.println("The filename is: " + newFileName);
        try {
            BitOutputStream outputStream = new BitOutputStream(new FileOutputStream(file));
            HashMap<Byte, Integer> dictionary = new HashMap<>();
            int key = 1;
            for (int i = 0; i < dataStream.length - 1; i++) {

                if (dictionary.containsKey(dataStream[i]) == false) {
                    key += 1;
                    dictionary.put(dataStream[i], key);
                }
                BitSequence sequence = new BitSequence(bitsPerSymbol);
                System.out.println("Wriiing for the " + i + "th time the " + dataStream[i]);
                sequence.setSequence(dictionary.get(dataStream[i]));
                outputStream.write(sequence);
            }

            if(outputStream.getCount() != 1) {
                System.out.println("Count was not equal to 1");
                BitSequence fillerSequence = new BitSequence(Main.BYTE_LEN - outputStream.getCount() + 1);
                fillerSequence.setSequence(0);
                outputStream.write(fillerSequence);
            }

            return dictionary;

        } catch (IOExcepgite) {
            e.printStackTrace();
            throw new FileNotFoundException(String.format(IOEXCEPTION_MESSAGE));
        }


    }

    /**
     * This method is meant to return the name of the compressed file the data would be
     * put into.
     *
     * @param inputFileName The file name for the input.
     * @return the output filename.
     */
    public String getCompressedFileName(String inputFileName) {
        String[] fileParts = inputFileName.split("\\.(?=[^\\.]+$)");;
        String outputFile = fileParts[0] + "-compressed." + fileParts[1];
        return outputFile;
    }

    public String getDictionaryFileName(String inputFileName) {
        String[] fileParts = inputFileName.split("")
    }

    /**
     * This method takes the number of of symbols for a given file and outputs
     * the number of bits we need to encode each letter in the file.
     *
     * I do a +1 because, we also need to accomodate for not being able to
     * use a null byte here.
     * @param numSymbols
     * @return
     */
    public int logCeil(int numSymbols) {
        return (int) (Math.ceil(Math.log(numSymbols + 1) / Math.log(2)));
    }
}
