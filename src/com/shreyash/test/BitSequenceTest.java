package com.shreyash.test;

import com.shreyash.main.helpers.BitSequence;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.AssertJUnit.assertEquals;

public class BitSequenceTest {

    @Test
    public void lengthTest() {

        BitSequence sequence = new BitSequence(4);
        assertEquals(sequence.getSequence().length, 4);
    }

    @Test
    public void writeTestOne() {

        BitSequence sequence = new BitSequence(4);

        sequence.setSequence(0);
        boolean[] checkSequence = {false, false, false, false};

        assertEquals(true, Arrays.equals(sequence.getSequence(), checkSequence));
    }

    @Test
    public void writeTestTwo() {

        BitSequence sequence = new BitSequence(4);
        sequence.setSequence(5);
        boolean[] checkSequence = {true, false, true, false};
        assertEquals(true, Arrays.equals(sequence.getSequence(), checkSequence));
    }


    @Test
    public void writeTestThree() {

        BitSequence sequence = new BitSequence(4);
        sequence.setSequence(12);
        boolean[] checkSequence = {false, false, true, true};
        assertEquals(true, Arrays.equals(sequence.getSequence(), checkSequence));
    }


    @Test
    public void writeTestFour() {

        BitSequence sequence = new BitSequence(4);
        sequence.setSequence(3);
        boolean[] checkSequence = {true, true, false, false};
        assertEquals(true, Arrays.equals(sequence.getSequence(), checkSequence));

    }

    @Test
    public void writeTestFive() {

        BitSequence sequence = new BitSequence(3);

        sequence.setSequence(7);
        boolean[] checkSequence = {true, true, true};
        assertEquals(true, Arrays.equals(sequence.getSequence(), checkSequence));
    }

    @Test
    public void writeTestSix() {

        BitSequence sequence = new BitSequence(5);
        sequence.setSequence(5);
        boolean[] checkSequence = {true, false, true, false, false};
        assertEquals(true, Arrays.equals(sequence.getSequence(), checkSequence));
    }

    @Test
    public void writeTestSeven() {

        BitSequence sequence = new BitSequence(6);
        sequence.setSequence(12);
        boolean[] checkSequence = {false, false, true, true, false, false};
        assertEquals(true, Arrays.equals(sequence.getSequence(), checkSequence));
    }

    @Test
    public void writeTestEight() {

        BitSequence sequence = new BitSequence(8);
        sequence.setSequence(65);
        boolean[] checkSequence = {true, false, false, false, false, false, true, false};
        assertEquals(true, Arrays.equals(sequence.getSequence(), checkSequence));

    }

    @Test
    public void writeTestNine() {
        BitSequence sequence = new BitSequence(8);
        sequence.setSequence(0);
        boolean[] checkSequence = {true, false, false, false, false, true, false, false};
        assertEquals(false, Arrays.equals(sequence.getSequence(), checkSequence));

    }
}
