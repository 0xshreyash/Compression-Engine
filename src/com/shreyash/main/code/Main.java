package code; /**
 * @Author: Shreyash Patodia
 */


import code.decode.IDecompressor;
import code.encode.ICompressor;
import code.encode.SimpleCompressor;
import code.decode.SimpleDecompressor;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class for the application, this is where the interaction with the
 * application is setup and run.
 */
public class Main {

    /** Length of the delimiter between lines */
    private static int delimiterLen = 100;
    /** Using a = sign as the char that makes up a delimiter */
    private static char delimiter = '=';
    /** Length of a byte */
    public static final int BYTE_LENGTH = 8;
    /** Length of an integer in bits */
    public static int INTEGER_LENGTH = 32;

    /**
     * Main method which is the point of start for the application
     * @param args command line args, no important for this application.
     */
    public static void main(String[] args) {

        printDelimiter(delimiterLen, delimiter);

        String openingLine = "Data Processing Application Starting...";
        printLine(openingLine);

        /* flag that keeps the application running until the user wants to exit */
        boolean keepRunning = true;

        while (keepRunning) {
            printDelimiter(delimiterLen, delimiter);

            /* Give user options to java.encode or java.decode */
            String optionOpening = "You can run this application in two modes";
            printLine(optionOpening);

            String optionLineOne = "-> Enter 1 to encode an input file";
            printLine(optionLineOne);

            String optionLineTwo = "-> Enter 2 to decode an encoded file";
            printLine(optionLineTwo);

            Scanner in = new Scanner(System.in);

            printDelimiter(delimiterLen, delimiter);
            boolean flag = false;

            /* Choose which mode to run in */
            while (!flag) {
                int mode;
                try {
                    mode = in.nextInt();
                }
                /*
                 * Prevent user from entering anything other than a number,
                 * the application does not exit when the user tries to mess with
                 * it, but allows the user another chance to try to use it correctly
                 */ catch (InputMismatchException e) {
                    in.next();
                    e.printStackTrace();
                    continue;
                }
                switch (mode) {
                    case 1:
                        enterEncodeMode();
                        flag = true;
                        break;
                    case 2:
                        enterDecodeMode();
                        flag = true;
                        break;
                    /* If the user picks an invalid option */
                    default:
                        String errorMessage = "Sorry! Incorrect mode entered, please see usage message below:";
                        printLine(errorMessage);
                        printLine(optionOpening);
                        printLine(optionLineOne);
                        printLine(optionLineTwo);
                        printDelimiter(delimiterLen, delimiter);
                }
            }

            /* An invalid input will be taken as no. */
            String askForExit = "If you want to exit the application enter yes else enter no";
            printLine(askForExit);
            String exit = in.next();
            if (exit.equalsIgnoreCase("yes")) {
                keepRunning = false;
            }

        }
    }

    /**
     * Method takes the file to be encoded as input and calls the compressor to
     * java.encode it as a file with the name <filename>-compressed.txt
     */
    public static void enterEncodeMode() {
        String startModeLine = "Entering encoding mode..";
        String inputFileRequest = "Please enter the input file you want to java.encode";
        String fileName = modeSetup(startModeLine, inputFileRequest);
        ICompressor compressor = new SimpleCompressor();
        try {
            String compressedFile = compressor.encode(fileName);
            printLine("Compressed file written to: " + compressedFile);
            printDelimiter(delimiterLen, delimiter);
        }
        // Catching the IOExceptions that might happen while reading and writing.
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Method helps setup both java.encode and java.decode mode by asking/taking input etc. s
     * @param startModeLine line to start mode
     * @param inputFileRequest the output asking for the input file
     * @return the name of the input file.
     */
    public static String modeSetup(String startModeLine, String inputFileRequest) {
        printDelimiter(delimiterLen, delimiter);
        printLine(startModeLine);
        printLine(inputFileRequest);
        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();
        printLine("File entered: " + fileName);
        printDelimiter(delimiterLen, delimiter);
        return fileName;

    }

    /**
     * Method takes the file to java.decode as input and calls on the decompressor to
     * java.decode the file. The reconstructed file is called <filename>-reconstructed.txt
     * Where <filename> corresponds to filename in <filename>-compressed.txt
     */
    public static void enterDecodeMode() {
        String startModeLine = "Entering decoding mode..";
        String inputFileRequest = "Please enter the input file you want to java.decode";
        String fileName = modeSetup(startModeLine, inputFileRequest);
        IDecompressor decompressor = new SimpleDecompressor();
        try {
            String decompressedFile = decompressor.decode(fileName);
            printLine("Reconstructed file written to: " + decompressedFile);
            printDelimiter(delimiterLen, delimiter);
        }
        // Catching the IOExceptions that might happen while reading and writing.
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stylistic method that prints a delimiter in a line
     * @param delimiterLen the number of times the delimiter char needs to be printed
     * @param delimiter the char to be used as a delimiter.
     */
    public static void printDelimiter(int delimiterLen, char delimiter) {
        for(int i = 0; i < delimiterLen; i++) {
            System.out.print(delimiter);
        }
        System.out.println();
    }

    /**
     * Method that prints a line by centre aligning it wrt the delimiter lines.
     * @param line the line to be printed.
     */
    public static void printLine(String line) {

        int leftPadding = (delimiterLen - line.length())/2;
        leftPadding = leftPadding>=0?leftPadding:0;
        int rightPadding = delimiterLen - leftPadding - line.length();
        rightPadding = rightPadding>=0?rightPadding:0;
        System.out.format("%" + leftPadding + "s" + line + "%" + rightPadding + "s\n", " ", " ");
    }

}
