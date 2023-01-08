package com.thegongoliers.output.actuators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.wpilibj.Solenoid;

import static org.mockito.Mockito.*;

public class GPistonTest {

    private GPiston piston;
    private Solenoid solenoid;

    @BeforeEach
    public void setup(){
        solenoid = mock(Solenoid.class);
        piston = new GPiston(solenoid);
    }

    @Test
    public void normalOperation(){
        when(solenoid.get()).thenReturn(false);
        assertFalse(piston.isInverted());
        assertFalse(piston.isExtended());
        assertTrue(piston.isRetracted());

        piston.extend();
        when(solenoid.get()).thenReturn(true);
        assertTrue(piston.isExtended());
        assertFalse(piston.isRetracted());
        verify(solenoid).set(true);

        piston.retract();
        when(solenoid.get()).thenReturn(false);
        assertFalse(piston.isExtended());
        assertTrue(piston.isRetracted());
        verify(solenoid).set(false);
    }

    @Test
    public void invertedOperation(){
        piston.setInverted(true);
        when(solenoid.get()).thenReturn(false);
        assertTrue(piston.isInverted());
        assertTrue(piston.isExtended());
        assertFalse(piston.isRetracted());

        piston.extend();
        when(solenoid.get()).thenReturn(false);
        assertTrue(piston.isExtended());
        assertFalse(piston.isRetracted());
        verify(solenoid).set(false);;

        piston.retract();
        when(solenoid.get()).thenReturn(true);
        assertFalse(piston.isExtended());
        assertTrue(piston.isRetracted());
        verify(solenoid).set(true);
    }

}