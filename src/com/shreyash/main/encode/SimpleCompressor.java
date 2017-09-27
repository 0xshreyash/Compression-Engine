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

/**
 * This is a class that is effective when encoding files of
 * data where the symbols will be in distribution corresponding
 * to uniform. I chose this method because I felt like most other
 * methods try to use the bias in number of occurrences of symbols
 * or combination of symbols in data.
 * <p>
 * The method works by taking an initial pass through the file and
 * mapping each symbol in the file to a byte code. Now, this byte code
 * starts off at value 00000001. It would then increase the byte code
 * for each new symbol it encounters. In the process using up to
 * ceil(log(n)) bits where n is the number of unique symbols and log
 * is to the base 2. I think this is as well as any method can do due
 * to the lack of bias in the data. This method would use up to 4 bits
 * per symbol for a file that has <= 16 symbols, as opposed to the
 * initial 8 bits, offering almost a 50% saving on the size of the file.
 * "Almost" because metadata like the number of symbols and the mapping
 * from symbols to their encoding also needs to be written to the file.
 * But the metadata size would be small as the file size continues to grow
 * without showing too much increase in the number of symbols in the file.
 */
public class SimpleCompressor implements ICompressor {

    /**
     * Error messages
     */
    private static String CANNOT_COMPRESS_MESSAGE = "Cannot compress the file any further" +
            " using SimpleCompressor. Sorry!";

    private static String IOEXCEPTION_MESSAGE = "There was an error while reading the file";

    /**
     * Length of an integer in bits
     */
    private static int INTEGER_LENGTH = 32;

    /**
     * The stream the compressed representation is being written to
     */
    private BitOutputStream outputStream;

    /**
     * This method is the method that is called when we want to
     * encode something and output it as file. The output of this
     * method is a file with the same name as that of the input file
     * but with a the suffix -compressed to the name. The extension of the
     * file is the same as the original file.
     *
     * @param fileName The name of the file to be compressed.
     * @throws IOException
     */
    @Override
    public void encode(String fileName) throws IOException {
        try {
            /* Reading in the data from the file */
            Path path = Paths.get(fileName);
            byte[] dataStream = Files.readAllBytes(path);

            /* Find out how many unique elements there are */
            HashMap<Byte, Integer> dictionary = getDictionary(dataStream);

            String compressedFileName = getCompressedFileName(fileName);
            int uniqueElements = dictionary.size();
            /* Using the unique elements to compute how many
             * bits of data will be needed
             */
            int bitsPerSymbol = logCeil(uniqueElements);

            /*
             * Say that the file cannot be compressed
             */
            if (bitsPerSymbol >= Main.BYTE_LENGTH) {

                Main.printLine(CANNOT_COMPRESS_MESSAGE);
                return;

            } else {

                File file = new File(compressedFileName);
                //System.out.println("The filename is: " + newFileName);
                this.outputStream = new BitOutputStream(new FileOutputStream(file));
                BitSequence numUniqueElements = new BitSequence(Main.BYTE_LENGTH);
                numUniqueElements.setSequence(uniqueElements);
                outputStream.write(numUniqueElements);
                for (Byte keyByte : dictionary.keySet()) {
                    System.out.println("The key byte is: " + keyByte);
                    BitSequence key = new BitSequence(Main.BYTE_LENGTH);
                    BitSequence value = new BitSequence(bitsPerSymbol);
                    key.setSequence(keyByte);
                    key.setSequence(dictionary.get(keyByte));
                    outputStream.write(key);
                    System.out.println("Printing sequence: " + key);
                    outputStream.write(value);
                }
                mapSymbolsToCode(compressedFileName, dataStream, dictionary, bitsPerSymbol);

            }
        }
        /* Exception handling */ catch (IOException e) {
            throw new IOException(IOEXCEPTION_MESSAGE);
        }
    }

    /**
     * This method starts with reading the file, and mapping a key
     * (starting at 1) to each unique symbol it encounters while
     * reading the file. This is the first pass of the program
     * and allows for some pre-processing to be done on the file
     * for easy compression.
     *
     * @param dataStream The data read as bytes as instructed.
     * @return the dictionary mapping codes to symbols
     */
    public HashMap<Byte, Integer> getDictionary(byte[] dataStream) {
        /* Counting the number of unique bytes */
        HashMap<Byte, Integer> dictionary = new HashMap<>();
        int value = 1;
        // -1 to not include the last file termination byte.
        for (int i = 0; i < dataStream.length - 1; i++) {
            if (dictionary.containsKey(dataStream[i]) == false) {
                //System.out.println("Putting " + dataStream[i] + " into the dictionary");
                dictionary.put(dataStream[i], value);
                value += 1;
            }
        }
        return dictionary;
    }

    /**
     * This method takes the output file, a datastream, the dictionary and the number of bits needed
     * to encode each symbol. It then outputs the encoded file into the output file by using the
     * dictionary.
     *
     * @param newFileName the file to output the compressed representation
     * @param dataStream the data to be encoded in bytes
     * @param dictionary the mapping from bytes to their encodings
     * @param bitsPerSymbol the number of bits in each symbol's code.
     * @return void
     * @throws IOException
     */
    public void mapSymbolsToCode(String newFileName, byte[] dataStream, HashMap<Byte, Integer> dictionary,
                                                   int bitsPerSymbol) throws IOException {

        File file = new File(newFileName);
        //System.out.println("The filename is: " + newFileName);
        try {
            for (int i = 0; i < dataStream.length - 1; i++) {
                BitSequence sequence = new BitSequence(bitsPerSymbol);
                System.out.println("Writing for the " + i + "th time the " + dataStream[i]);
                sequence.setSequence(dictionary.get(dataStream[i]));
                outputStream.write(sequence);
            }
            if (outputStream.getCount() != 1) {
                System.out.println("Count was not equal to 1");
                BitSequence fillerSequence = new BitSequence(Main.BYTE_LENGTH - outputStream.getCount() + 1);
                fillerSequence.setSequence(0);
                outputStream.write(fillerSequence);
            }

        } catch (IOException e) {
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
        String[] fileParts = inputFileName.split("\\.(?=[^\\.]+$)");
        ;
        String outputFile = fileParts[0] + "-compressed." + fileParts[1];
        return outputFile;
    }

    /**
     * This method takes the number of of symbols for a given file and outputs
     * the number of bits we need to encode each letter in the file.
     * <p>
     * I do a +1 because, we also need to accommodate for not being able to
     * use a null byte here.
     *
     * @param numSymbols the number of unique symbols in the file
     * @return the number of bits needed to encode the symbols
     */
    public int logCeil(int numSymbols) {
        return (int) (Math.ceil(Math.log(numSymbols + 1) / Math.log(2)));
    }
}
