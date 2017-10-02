/**
 * @Author: Shreyash Patodia
 */
package code.helpers;

import code.Main;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Bit input stream class that reads the file as a sequence of
 * bytes and allows the decompressor to read the data bit by
 * bit using the BitSequence class.
 */
public class BitInputStream {

    /** The file read in as a sequence of bytes */
    private byte[] dataStream;
    /** Number of bits in the buffer */
    private int bufferIndex;
    /** The index of the sequence of bytes already read */
    private int readingIndex;
    /** The sequence that is currently being read */
    private BitSequence currentlyReading;
    /** The boolean sequence representing bits */
    private boolean[] sequence;

    public BitInputStream(String fileName) throws IOException {
        /* Reading in the data from the file */
        Path path = Paths.get(fileName);
        this.dataStream = Files.readAllBytes(path);
        this.bufferIndex = -1;
        this.readingIndex = 0;
        this.currentlyReading = new BitSequence(Main.BYTE_LENGTH);
        this.sequence = null;
    }

    /**
     * Reading lengthToRead bits from the dataStream and returning it
     * as a number
     * @param lengthToRead the number of bits to be read
     * @return the number representation of the bits
     */
    public int read(int lengthToRead) {
        int read = 0;
        int num = 0;

        while(read < lengthToRead) {
            /* Read another byte if more data needs to be read */
            if(bufferIndex < 0) {
                //System.out.println("The actual sequence is: ");
                currentlyReading = new BitSequence(Main.BYTE_LENGTH);
                currentlyReading.setSequence(dataStream[readingIndex]);
                readingIndex++;
                bufferIndex = Main.BYTE_LENGTH - 1;
                sequence = currentlyReading.getSequence();

            }
            // Left shift the number
            //System.out.println("Buffer size is: " + bufferIndex);
            num = num*2 + (sequence[bufferIndex]?1:0);
            read++;
            bufferIndex--;
        }
        return num;
    }
}
