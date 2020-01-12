package com.thegongoliers.input.switches;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SwitchTest {

    private Switch switch1, switch2;

    @Before
    public void setup(){
        switch1 = mock(Switch.class);
        switch2 = mock(Switch.class);

        when(switch1.isTriggered()).thenReturn(false);
        when(switch2.isTriggered()).thenReturn(false);
    }

    @Test
    public void testInvert(){
        Switch inverted = Switch.invert(switch1);

        assertTrue(inverted.isTriggered());

        when(switch1.isTriggered()).thenReturn(true);

        assertFalse(inverted.isTriggered());

        when(switch1.isTriggered()).thenReturn(false);

        assertTrue(inverted.isTriggered());
    }

    @Test
    public void testAnd(){
        Switch and = Switch.and(switch1, switch2);

        // F & F
        assertFalse(and.isTriggered());

        // T & F
        when(switch1.isTriggered()).thenReturn(true);
        assertFalse(and.isTriggered());

        // F & T
        when(switch1.isTriggered()).thenReturn(false);
        when(switch2.isTriggered()).thenReturn(true);
        assertFalse(and.isTriggered());

        // T & T
        when(switch1.isTriggered()).thenReturn(true);
        when(switch2.isTriggered()).thenReturn(true);
        assertTrue(and.isTriggered());
    }

    @Test
    public void testOr(){
        Switch or = Switch.or(switch1, switch2);

        // F & F
        assertFalse(or.isTriggered());

        // T & F
        when(switch1.isTriggered()).thenReturn(true);
        assertTrue(or.isTriggered());

        // F & T
        when(switch1.isTriggered()).thenReturn(false);
        when(switch2.isTriggered()).thenReturn(true);
        assertTrue(or.isTriggered());

        // T & T
        when(switch1.isTriggered()).thenReturn(true);
        when(switch2.isTriggered()).thenReturn(true);
        assertTrue(or.isTriggered());
    }

    @Test
    public void testXor(){
        Switch xor = Switch.xor(switch1, switch2);

        // F & F
        assertFalse(xor.isTriggered());

        // T & F
        when(switch1.isTriggered()).thenReturn(true);
        assertTrue(xor.isTriggered());

        // F & T
        when(switch1.isTriggered()).thenReturn(false);
        when(switch2.isTriggered()).thenReturn(true);
        assertTrue(xor.isTriggered());

        // T & T
        when(switch1.isTriggered()).thenReturn(true);
        when(switch2.isTriggered()).thenReturn(true);
        assertFalse(xor.isTriggered());
    }

}