package com.thegongoliers.math.lookup;

import com.thegongoliers.GongolieriumException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleLookupTableTest {

    @Test
    public void retrievesWithExactKeys(){
        LookupTable lookupTable = new SimpleLookupTable(new double[]{1, 2, 3, 4});
        assertEquals(1, lookupTable.get(0), 0.0);
        assertEquals(4, lookupTable.get(3), 0.0);
    }

    @Test
    public void usesClosestKey(){
        LookupTable lookupTable = new SimpleLookupTable(new double[]{1, 2, 3, 4});
        assertEquals(3, lookupTable.get(1.7), 0.0);
        assertEquals(3, lookupTable.get(2.2), 0.0);

        assertEquals(1, lookupTable.get(-0.6), 0.0);
        assertEquals(4, lookupTable.get(4.6), 0.0);
    }

    @Test
    public void usesOffset(){
        LookupTable lookupTable = new SimpleLookupTable(new double[]{1, 2, 3, 4}, 1.0);
        assertEquals(2, lookupTable.get(1.7), 0.0);
        assertEquals(2, lookupTable.get(2.2), 0.0);
        assertEquals(3, lookupTable.get(3), 0.0);
    }

    @Test
    public void throwsOnNullArray(){
        assertThrows(GongolieriumException.class, () -> new SimpleLookupTable(null));
    }

    @Test
    public void throwsOnEmptyArray(){
        assertThrows(GongolieriumException.class, () -> new SimpleLookupTable(new double[]{}));
    }

}