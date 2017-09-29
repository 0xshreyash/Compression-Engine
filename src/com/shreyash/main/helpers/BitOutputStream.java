package com.shreyash.main.helpers;

import com.shreyash.main.Main;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The output stream that takes a sequence of
 * bits and outputs them once every byte is completed.
 */
public class BitOutputStream {

    /** The stream to output to */
    private OutputStream out;
    /** The buffer storing the bits until a byte is reached */
    private boolean[] buffer;
    /** The number of things on the buffer */
    private int count;

    public BitOutputStream(OutputStream out) {
        this.out = out;
        this.count = 1;
        this.buffer = new boolean[Main.BYTE_LENGTH];
    }

    /** Takes a bit sequence, and stores it in the buffer,
     *  and writes to the output stream once the buffer is full
     * @param x the bit sequence to write out to the output stream
     * @throws IOException
     */
    public void write(BitSequence x) throws IOException {

        boolean[] sequence = x.getSequence();
        int index = sequence.length - 1;
        while(index >= 0) {
            this.buffer[Main.BYTE_LENGTH - count] = sequence[index];
            //System.out.println("Buffer index " + (Main.BYTE_LENGTH - count) + " contains " + this.buffer[8 - count]);
            if (this.count == Main.BYTE_LENGTH) {
                int num = 0;
                for (int i = Main.BYTE_LENGTH - 1; i >= 0; i--) {
                    num = 2 * num + (this.buffer[i] ? 1 : 0);
                    //System.out.println("Number is: " + num);
                }
                //System.out.println("Writing to the file: " + num);
                this.out.write(num);
                this.count = 0;
            }
            this.count++;
            index--;
        }
    }

    /**
     * Fills the buffer in order to flush the final bits in the file and the
     * closes the output stream.
     * @throws IOException
     */
    public void close() throws IOException {
        if (this.count != 1) {
            //System.out.println("Count was not equal to 1");
            BitSequence fillerSequence = new BitSequence(Main.BYTE_LENGTH - this.count + 1);
            fillerSequence.setSequence(0);
            this.write(fillerSequence);
        }
        this.out.close();
    }

    /**
     * Getter for the count.
     * @return
     */
    public int getCount() {
        return count;
    }




}