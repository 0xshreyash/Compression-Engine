/**
 * @Author: Shreyash Patodia
 */
package code.helpers;

import java.util.Arrays;
import java.util.Stack;

/**
 * Class that represents a bit sequence, the bit sequence is stored as an
 * array of booleans with true representing 1 and 0 representing false.
 * I needed this class since java doesn't allow bit level manipulation, so
 * I had to work around that.
 */
public class BitSequence {

    /**
     * The sequence of bits as booleans
     */
    private boolean[] sequence;

    /**
     * The length of the sequence
     */
    private int length;

    public BitSequence(int length) {
        this.length = length;
        sequence = new boolean[length];
        Arrays.fill(sequence, false);
    }

    /**
     * This method is used to the set the sequence of bits using a number,
     * this number can be positive or negative. The sequence then holds
     * the representation of the number as bits (booleans). I ensure that
     * the number can surely be represented given the max. length of the
     * boolean array.
     *
     * @param number the number the bit sequence needs to represent.
     */
    public void setSequence(int number) {
        //System.out.println("Setting sequence to value: " + number);
        Stack<Boolean> bits = new Stack<>();
        int count = 0;
        //System.out.println("The number passed to me was: " + number);

        /* Only calculate the representation of positive numbers, but then
         * convert to the negative representation later on.
         */
        int copyNumber = number;
        if (number < 0) {
            number = -number;
        }
        /* Fine the 2's complement rep of the number */
        while (number > 0) {
            /* If the residue is 1 then it's a true else it's a false */
            int residue = number % 2;
            //System.out.println("Number is now: " + number + " which gave residue " + residue);
            //System.out.println("Residue is: " + residue);
            boolean toPush = residue == 1 ? true : false;
            //System.out.println("Now pushing: " + toPush);
            bits.push(toPush);
            count++;
            number /= 2;
        }
        /* Assign the values on teh stack to the sequence */
        int i = count - 1;
        while (i >= 0) {
            //System.out.println("Printing");
            this.sequence[i] = bits.pop();
            i--;
        }
        i = 0;

        /* Turn from positive to negative representation if the number was originally
         * negative.
         */
        boolean flip = false;
        //System.out.println("The sequence for " + number + " is " + this);
        if (copyNumber < 0) {
            while (i < this.sequence.length) {
                this.sequence[i] = flip ? !this.sequence[i] : this.sequence[i];
                if (this.sequence[i] == true) {
                    flip = true;
                }
                i++;
            }
        }
        //System.out.println("Sequence corresponding to: " + copyNumber + " is " + this);
    }

    /**
     * Standard equals method
     *
     * @param o object to compare with.s
     * @return true if the objects are equal,
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitSequence that = (BitSequence) o;

        if (length != that.length) return false;
        return Arrays.equals(sequence, that.sequence);
    }

    /**
     * Standard hashCode method.
     *
     * @return the hashCode of the object
     */
    @Override
    public int hashCode() {
        int result = Arrays.hashCode(sequence);
        result = 31 * result + length;
        return result;
    }

    /**
     * Standard toString method.
     *
     * @return the contents of the sequence
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for (boolean val : this.sequence) {

            sb.append(val + ", ");
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
        return sb.toString();
    }

    /**
     * Get sequence stored in the BitSequence
     *
     * @return the sequence
     */
    public boolean[] getSequence() {
        return sequence;
    }

}
