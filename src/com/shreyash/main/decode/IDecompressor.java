package com.shreyash.main.decode;

import java.io.IOException;

public interface IDecompressor {

    void decode(String fileName) throws IOException;
}
