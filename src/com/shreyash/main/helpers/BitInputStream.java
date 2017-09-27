package com.shreyash.main.helpers;

import com.shreyash.main.Main;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream {

    private InputStream inputStream;
    private int number;
    private int count;

    public BitInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.count = Main.BYTE_LENGTH;
        this.number = 0;
    }

    public boolean read() throws IOException {

        if (this.count == Main.BYTE_LENGTH) {
            this.number = this.inputStream.read();
            this.count = 0;
        }

        boolean x = (number %2 == 1);
        number /= 2;
        this.count++;
        return x;
    }

    public void close() throws IOException {
        this.inputStream.close();
    }

}
