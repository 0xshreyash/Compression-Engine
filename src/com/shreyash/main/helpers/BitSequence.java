package com.shreyash.main.helpers;

import java.util.Arrays;
import java.util.Stack;

public class BitSequence {

    private boolean[] sequence;
    private int length;

    public BitSequence(int length) {
        this.length = length;
        sequence = new boolean[length];
        Arrays.fill(sequence, false);
    }

    public boolean[] getSequence() {
        return sequence;
    }

    public void setSequence(boolean[] sequence) {
        this.sequence = sequence;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setSequence(int number) {
        //System.out.println("Setting sequence to value: " + number);
        Stack<Boolean> bits = new Stack<>();
        int count = 0;
        System.out.println("The number passed to me was: " + number);
        int copyNumber = number;
        if(number < 0) {
            number = -number;
        }
        while(number > 0) {
            int residue = number % 2;
            //System.out.println("Number is now: " + number + " which gave residue " + residue);
            //System.out.println("Residue is: " + residue);
            boolean toPush = residue==1?true:false;
            //System.out.println("Now pushing: " + toPush);
            bits.push(toPush);
            count++;
            number /= 2;
        }

        int i = count - 1;
        while(i >= 0) {
            //System.out.println("Printing");
            this.sequence[i] = bits.pop();
            i--;
        }
        i = 0;
        boolean flip = false;
        //System.out.println("The sequence for " + number + " is " + this);
        if(copyNumber < 0) {
            while(i < this.sequence.length) {
                this.sequence[i] = flip ? !this.sequence[i] : this.sequence[i];
                if (this.sequence[i] == true) {
                    flip = true;
                }
                i++;
            }
        }
        //System.out.println("Sequence corresponding to: " + copyNumber + " is " + this);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitSequence that = (BitSequence) o;

        if (length != that.length) return false;
        return Arrays.equals(sequence, that.sequence);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(sequence);
        result = 31 * result + length;
        return result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for(boolean val : this.sequence) {

            sb.append(val + ", ");
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
        return sb.toString();
    }
}
