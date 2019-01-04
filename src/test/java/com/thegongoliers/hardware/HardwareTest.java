package com.thegongoliers.hardware;

import com.thegongoliers.mockHardware.input.MockGyro;
import com.thegongoliers.mockHardware.input.MockSwitch;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import org.junit.Test;

import static org.junit.Assert.*;

public class HardwareTest {

    @Test
    public void testMakeTrigger(){
        MockSwitch s = new MockSwitch();
        Trigger t = Hardware.makeTrigger(s::isTriggered);

        assertNotNull(t);

        s.setTriggered(false);
        assertFalse(t.get());

        s.setTriggered(true);
        assertTrue(t.get());
    }

    @Test
    public void testSwitchToTrigger(){
        MockSwitch s = new MockSwitch();
        Trigger t = Hardware.switchToTrigger(s);

        assertNotNull(t);

        s.setTriggered(false);
        assertFalse(t.get());

        s.setTriggered(true);
        assertTrue(t.get());
    }

    @Test
    public void testMakeButton(){
        MockSwitch s = new MockSwitch();
        Button t = Hardware.makeButton(s::isTriggered);

        assertNotNull(t);

        s.setTriggered(false);
        assertFalse(t.get());

        s.setTriggered(true);
        assertTrue(t.get());
    }

    @Test
    public void testTriggerToButton(){
        MockSwitch s = new MockSwitch();
        Trigger t = Hardware.switchToTrigger(s);
        Button b = Hardware.triggerToButton(t);

        assertNotNull(t);

        s.setTriggered(false);
        assertFalse(b.get());

        s.setTriggered(true);
        assertTrue(b.get());
    }

    @Test
    public void testInvertGyro(){
        MockGyro gyro = new MockGyro();
        Gyro g = Hardware.invertGyro(gyro);

        assertNotNull(g);

        gyro.setAngle(10);
        assertEquals(-10, g.getAngle(), 0.0001);

        gyro.setAngle(-20);
        assertEquals(20, g.getAngle(), 0.0001);

        gyro.setRate(0.5);
        assertEquals(-0.5, g.getRate(), 0.0001);

        gyro.setRate(-0.5);
        assertEquals(0.5, g.getRate(), 0.0001);

        gyro.reset();
        assertEquals(0, g.getAngle(), 0.0001);
    }

}