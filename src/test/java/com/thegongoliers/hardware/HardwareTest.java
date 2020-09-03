package com.thegongoliers.hardware;

import com.thegongoliers.input.switches.ResettableSwitch;
import com.thegongoliers.input.switches.Switch;
import com.thegongoliers.output.interfaces.Drivetrain;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HardwareTest {

    @Test
    public void testMakeTrigger(){
        Switch s = mock(Switch.class);
        Trigger t = Hardware.makeTrigger(s::isTriggered);

        assertNotNull(t);

        when(s.isTriggered()).thenReturn(false);
        assertFalse(t.get());

        when(s.isTriggered()).thenReturn(true);
        assertTrue(t.get());
    }

    @Test
    public void testSwitchToTrigger(){
        Switch s = mock(Switch.class);
        Trigger t = Hardware.switchToTrigger(s);

        assertNotNull(t);

        when(s.isTriggered()).thenReturn(false);
        assertFalse(t.get());

        when(s.isTriggered()).thenReturn(true);
        assertTrue(t.get());
    }

    @Test
    public void testMakeButton(){
        Switch s = mock(Switch.class);
        Button t = Hardware.makeButton(s::isTriggered);

        assertNotNull(t);

        when(s.isTriggered()).thenReturn(false);
        assertFalse(t.get());

        when(s.isTriggered()).thenReturn(true);
        assertTrue(t.get());
    }

    @Test
    public void testCombineButtons(){
        Switch s1 = mock(Switch.class);
        Switch s2 = mock(Switch.class);
        Switch s3 = mock(Switch.class);

        Button button1 = Hardware.makeButton(s1::isTriggered);
        Button button2 = Hardware.makeButton(s2::isTriggered);
        Button button3 = Hardware.makeButton(s3::isTriggered);

        Button combined = Hardware.combineButtons(button1, button2, button3);

        // When all buttons are released, it should be false
        when(s1.isTriggered()).thenReturn(false);
        when(s2.isTriggered()).thenReturn(false);
        when(s3.isTriggered()).thenReturn(false);

        assertFalse(combined.get());

        // When only one button is pressed, it should be false
        when(s1.isTriggered()).thenReturn(true);
        when(s2.isTriggered()).thenReturn(false);
        when(s3.isTriggered()).thenReturn(false);

        assertFalse(combined.get());

        // When only two buttons are pressed, it should be false
        when(s1.isTriggered()).thenReturn(false);
        when(s2.isTriggered()).thenReturn(true);
        when(s3.isTriggered()).thenReturn(true);

        assertFalse(combined.get());

        // When all buttons are pressed, it should be true
        when(s1.isTriggered()).thenReturn(true);
        when(s2.isTriggered()).thenReturn(true);
        when(s3.isTriggered()).thenReturn(true);

        assertTrue(combined.get());

    }

    @Test
    public void testTriggerToButton(){
        Switch s = mock(Switch.class);
        Trigger t = Hardware.switchToTrigger(s);
        Button b = Hardware.triggerToButton(t);

        assertNotNull(t);

        when(s.isTriggered()).thenReturn(false);
        assertFalse(b.get());

        when(s.isTriggered()).thenReturn(true);
        assertTrue(b.get());
    }

    @Test
    public void testMakeSwitch(){
        Switch mockSwitch = mock(Switch.class);

        Switch s = Hardware.makeSwitch(mockSwitch::isTriggered);

        assertNotNull(s);

        when(mockSwitch.isTriggered()).thenReturn(false);
        assertFalse(s.isTriggered());

        when(mockSwitch.isTriggered()).thenReturn(true);
        assertTrue(s.isTriggered());
    }

    @Test
    public void testMakeResettableSwitchFromSwitch(){
        Switch mockSwitch = mock(Switch.class);

        ResettableSwitch s = Hardware.makeResettableSwitch(mockSwitch);
        assertNotNull(s);

        when(mockSwitch.isTriggered()).thenReturn(false);
        assertFalse(s.isTriggered());

        when(mockSwitch.isTriggered()).thenReturn(true);
        assertTrue(s.isTriggered());

        when(mockSwitch.isTriggered()).thenReturn(false);
        assertTrue(s.isTriggered());

        s.reset();
        assertFalse(s.isTriggered());
    }

    @Test
    public void testInvertGyro(){
        Gyro gyro = mock(Gyro.class);
        Gyro g = Hardware.invertGyro(gyro);

        assertNotNull(g);

        when(gyro.getAngle()).thenReturn(10.0);
        assertEquals(-10, g.getAngle(), 0.0001);

        when(gyro.getAngle()).thenReturn(-20.0);
        assertEquals(20, g.getAngle(), 0.0001);

        when(gyro.getRate()).thenReturn(0.5);
        assertEquals(-0.5, g.getRate(), 0.0001);

        when(gyro.getRate()).thenReturn(-0.5);
        assertEquals(0.5, g.getRate(), 0.0001);
    }


    @Test
    public void testInvertPotentiometer(){
        Potentiometer potentiometer = mock(Potentiometer.class);
        Potentiometer p = Hardware.invertPotentiometer(potentiometer);

        // Goes from 1000 -> 900, but it should be 0 -> 100

        double rawAngle = 1000;
        double offset = 1000;

        assertNotNull(p);

        when(potentiometer.get()).thenReturn(rawAngle - offset);
        assertEquals(0, p.get(), 0.001);

        rawAngle = 900;
        when(potentiometer.get()).thenReturn(rawAngle - offset);
        assertEquals(100, p.get(), 0.001);

        rawAngle = 1100;
        when(potentiometer.get()).thenReturn(rawAngle - offset);
        assertEquals(-100, p.get(), 0.001);

    }

    @Test
    public void testCreatingDrivetrain(){
        DifferentialDrive differentialDrive = mock(DifferentialDrive.class);
        Drivetrain drivetrain = Hardware.createDrivetrain(differentialDrive);

        drivetrain.stop();
        verify(differentialDrive).stopMotor();

        drivetrain.arcade(1.0, 0.5);
        verify(differentialDrive).arcadeDrive(1.0, 0.5, false);

        drivetrain.tank(-1.0, 1.0);
        verify(differentialDrive).tankDrive(-1.0, 1.0, false);

    }


    @Test
    public void convertsVoltageToPWM(){
        assertEquals(0, Hardware.voltageToPWM(0, () -> 10), 0.0001);
        assertEquals(0, Hardware.voltageToPWM(0, null), 0.0001);
        assertEquals(0, Hardware.voltageToPWM(0, () -> 0), 0.0001);
        assertEquals(0.4, Hardware.voltageToPWM(4, () -> 10), 0.0001);
        assertEquals(-0.4, Hardware.voltageToPWM(-4, () -> 10), 0.0001);
        assertEquals(1, Hardware.voltageToPWM(12, () -> 10), 0.0001);
        assertEquals(-1, Hardware.voltageToPWM(-12, () -> 10), 0.0001);
    }

    

}