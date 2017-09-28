package com.shreyash.main;

import com.shreyash.main.encode.SimpleCompressor;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static int delimiterLen = 100;
    private static char delimiter = '=';
    public static final int BYTE_LENGTH = 8;
    /**
     * Length of an integer in bits
     */
    public static int INTEGER_LENGTH = 32;

    public static void main(String[] args) {

        printDelimiter(delimiterLen, delimiter);

        String openingLine = "Data Processing Application Starting...";
        printLine(openingLine);
        boolean keepRunning = true;
        while(keepRunning) {
            printDelimiter(delimiterLen, delimiter);

            String optionOpening = "You can run this application in two modes";
            printLine(optionOpening);

            String optionLineOne = "-> Enter 1 to encode an input file";
            printLine(optionLineOne);

            String optionLineTwo = "-> Enter 2 to decode an encoded file";
            printLine(optionLineTwo);

            Scanner in = new Scanner(System.in);

            printDelimiter(delimiterLen, delimiter);
            boolean flag = false;

            while (!flag) {
                int mode = -1;
                try {
                    mode = in.nextInt();
                }
                catch(InputMismatchException e) {
                    //mode = -1;
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
                    default:
                        String errorMessage = "Sorry! Incorrect mode entered, please see usage message below:";
                        printLine(errorMessage);
                        printLine(optionOpening);
                        printLine(optionLineOne);
                        printLine(optionLineTwo);
                        printDelimiter(delimiterLen, delimiter);
                }

            }

            /*
             * An invalid input will be taken as no.
             */
            String askForExit = "If you want to exit the application enter yes else enter no";
            printLine(askForExit);
            String exit = in.next();
            if(exit.equalsIgnoreCase("yes")) {
                keepRunning = false;
            }

        }
    }

    public static void enterEncodeMode() {
        String startModeLine = "Entering encoding mode..";
        String inputFileRequest = "Please enter the input file you want to encode";
        String fileName = modeSetup(startModeLine, inputFileRequest);
        SimpleCompressor compressor = new SimpleCompressor();
        try {
            compressor.encode(fileName);
        }
        catch(IOException e){
            
        }


    }

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


    public static void enterDecodeMode() {
        String startModeLine = "Entering decoding mode..";
        String inputFileRequest = "Please enter the input file you want to encode";
        String fileName = modeSetup(startModeLine, inputFileRequest);
    }
    public static void printDelimiter(int delimiterLen, char delimiter) {
        for(int i = 0; i < delimiterLen; i++) {
            System.out.print(delimiter);
        }
        System.out.println();
    }

    public static void printLine(String line) {

        int leftPadding = (delimiterLen - line.length())/2;
        leftPadding = leftPadding>=0?leftPadding:0;
        int rightPadding = delimiterLen - leftPadding - line.length();
        rightPadding = rightPadding>=0?rightPadding:0;
        System.out.format("%" + leftPadding + "s" + line + "%" + rightPadding + "s\n", " ", " ");
    }

}
