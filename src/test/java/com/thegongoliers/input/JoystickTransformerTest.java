package com.thegongoliers.input;

import org.junit.Test;

import static org.junit.Assert.*;

public class JoystickTransformerTest {

    @Test
    public void scaledDeadzone() {
        assertEquals(0, JoystickTransformer.scaledDeadzone(0.1, 0.15), 0.0001);
        assertEquals(0, JoystickTransformer.scaledDeadzone(0.15, 0.15), 0.0001);
        assertEquals(1, JoystickTransformer.scaledDeadzone(1, 0.15), 0.0001);
        assertEquals(0.5, JoystickTransformer.scaledDeadzone(0.75, 0.5), 0.0001);
        assertEquals(-0.25, JoystickTransformer.scaledDeadzone(-0.625, 0.5), 0.0001);
    }

    @Test
    public void deadzone() {
        assertEquals(0, JoystickTransformer.deadzone(0.1, 0.15), 0.0001);
        assertEquals(0.15, JoystickTransformer.deadzone(0.15, 0.15), 0.0001);
        assertEquals(1, JoystickTransformer.deadzone(1, 0.15), 0.0001);
        assertEquals(0.75, JoystickTransformer.deadzone(0.75, 0.5), 0.0001);
        assertEquals(-0.625, JoystickTransformer.deadzone(-0.625, 0.5), 0.0001);
    }

    @Test
    public void sensitivity() {
        assertEquals(0, JoystickTransformer.sensitivity(0, 0.15), 0.0001);
        assertEquals(0.15, JoystickTransformer.sensitivity(1, 0.15), 0.0001);
        assertEquals(0.075, JoystickTransformer.sensitivity(0.5, 0.15), 0.0001);
        assertEquals(-0.5, JoystickTransformer.sensitivity(-1, 0.5), 0.0001);
    }

    @Test
    public void power() {
        assertEquals(0, JoystickTransformer.power(0, 2), 0.0001);
        assertEquals(-1, JoystickTransformer.power(-1, 2), 0.0001);
        assertEquals(0.25, JoystickTransformer.power(0.5, 2), 0.0001);
        assertEquals(-0.125, JoystickTransformer.power(-0.5, 3), 0.0001);
        assertEquals(-1, JoystickTransformer.power(-1, 3), 0.0001);
    }

}