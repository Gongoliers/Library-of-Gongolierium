package com.thegongoliers.input.switches;

import com.thegongoliers.mockHardware.input.MockSwitch;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SwitchTest {

    private MockSwitch switch1, switch2;

    @Before
    public void setup(){
        switch1 = new MockSwitch();
        switch2 = new MockSwitch();

        switch1.setTriggered(false);
        switch2.setTriggered(false);
    }

    @Test
    public void testInvert(){
        Switch inverted = switch1.invert();

        assertTrue(inverted.isTriggered());

        switch1.setTriggered(true);

        assertFalse(inverted.isTriggered());

        switch1.setTriggered(false);

        assertTrue(inverted.isTriggered());
    }

    @Test
    public void testAnd(){
        Switch and = Switch.and(switch1, switch2);

        // F & F
        assertFalse(and.isTriggered());

        // T & F
        switch1.setTriggered(true);
        assertFalse(and.isTriggered());

        // F & T
        switch1.setTriggered(false);
        switch2.setTriggered(true);
        assertFalse(and.isTriggered());

        // T & T
        switch1.setTriggered(true);
        switch2.setTriggered(true);
        assertTrue(and.isTriggered());
    }

    @Test
    public void testOr(){
        Switch or = Switch.or(switch1, switch2);

        // F & F
        assertFalse(or.isTriggered());

        // T & F
        switch1.setTriggered(true);
        assertTrue(or.isTriggered());

        // F & T
        switch1.setTriggered(false);
        switch2.setTriggered(true);
        assertTrue(or.isTriggered());

        // T & T
        switch1.setTriggered(true);
        switch2.setTriggered(true);
        assertTrue(or.isTriggered());
    }

    @Test
    public void testXor(){
        Switch xor = Switch.xor(switch1, switch2);

        // F & F
        assertFalse(xor.isTriggered());

        // T & F
        switch1.setTriggered(true);
        assertTrue(xor.isTriggered());

        // F & T
        switch1.setTriggered(false);
        switch2.setTriggered(true);
        assertTrue(xor.isTriggered());

        // T & T
        switch1.setTriggered(true);
        switch2.setTriggered(true);
        assertFalse(xor.isTriggered());
    }

}