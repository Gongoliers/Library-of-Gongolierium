package com.thegongoliers.output;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PistonTest {

    private Piston piston;
    private MockSolenoid solenoid;

    @Before
    public void setup(){
        solenoid = new MockSolenoid();
        piston = new Piston(solenoid);
    }

    @Test
    public void normalOperation(){
        assertFalse(piston.isInverted());
        assertFalse(piston.isExtended());
        assertTrue(piston.isRetracted());
        assertFalse(solenoid.get());

        piston.extend();
        assertTrue(piston.isExtended());
        assertFalse(piston.isRetracted());
        assertTrue(solenoid.get());

        piston.retract();
        assertFalse(piston.isExtended());
        assertTrue(piston.isRetracted());
        assertFalse(solenoid.get());
    }

    @Test
    public void invertedOperation(){
        piston.setInverted(true);
        assertTrue(piston.isInverted());
        assertTrue(piston.isExtended());
        assertFalse(piston.isRetracted());
        assertFalse(solenoid.get());

        piston.extend();
        assertTrue(piston.isExtended());
        assertFalse(piston.isRetracted());
        assertFalse(solenoid.get());

        piston.retract();
        assertFalse(piston.isExtended());
        assertTrue(piston.isRetracted());
        assertTrue(solenoid.get());
    }

}