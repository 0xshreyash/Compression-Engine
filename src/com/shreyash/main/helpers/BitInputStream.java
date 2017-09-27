package com.shreyash.main.helpers;

import com.shreyash.main.Main;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream {

    private InputStream in;
    private int num;
    private int count;

    public BitInputStream(InputStream in) {
        this.in = in;
        this.count = Main.BYTE_LEN;
        this.num = 0;
    }

    public boolean read() throws IOException {

        if (this.count == Main.BYTE_LEN){
            this.num = this.in.read();
            this.count = 0;
        }

        boolean x = (num%2 == 1);
        num /= 2;
        this.count++;
        return x;
    }

    public void close() throws IOException {
        this.in.close();
    }

}
