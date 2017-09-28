package com.shreyash.main.helpers;

import com.shreyash.main.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BitInputStream {

    private byte[] dataStream;
    // Number of bits in the buffer
    private int bufferIndex;
    private int readingIndex;
    private BitSequence currentlyReading;
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

    public int read(int lengthToRead) {
        // Number of bits we need to read is = the lengt
        int read = 0;
        int num = 0;

        while(read < lengthToRead) {
            if(bufferIndex < 0) {
                System.out.println("The actual sequence is: ");
                currentlyReading = new BitSequence(Main.BYTE_LENGTH);
                currentlyReading.setSequence(dataStream[readingIndex]);
                readingIndex++;
                bufferIndex = Main.BYTE_LENGTH - 1;
                sequence = currentlyReading.getSequence();

            }
            System.out.println("Buffer size is: " + bufferIndex);
            num = num*2 + (sequence[bufferIndex]?1:0);
            read++;
            bufferIndex--;
        }
        return num;
    }

    public boolean hasMoreElements() {
        if(readingIndex < dataStream.length)
            return true;
        return bufferIndex < 0;
    }
}
