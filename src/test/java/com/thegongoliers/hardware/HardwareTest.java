package com.thegongoliers.hardware;

import com.thegongoliers.input.switches.ResettableSwitch;
import com.thegongoliers.input.switches.Switch;
import com.thegongoliers.output.interfaces.Drivetrain;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HardwareTest {

    @BeforeEach
    public void setup(){
        Hardware.overrideEventLoop = new EventLoop();
    }

    @Test
    public void testMakeTrigger(){
        Switch s = mock(Switch.class);
        Trigger t = Hardware.makeTrigger(s::isTriggered);

        assertNotNull(t);

        when(s.isTriggered()).thenReturn(false);
        assertFalse(t.getAsBoolean());

        when(s.isTriggered()).thenReturn(true);
        assertTrue(t.getAsBoolean());
    }

    @Test
    public void testSwitchToTrigger(){
        Switch s = mock(Switch.class);
        Trigger t = Hardware.switchToTrigger(s);

        assertNotNull(t);

        when(s.isTriggered()).thenReturn(false);
        assertFalse(t.getAsBoolean());

        when(s.isTriggered()).thenReturn(true);
        assertTrue(t.getAsBoolean());
    }

    @Test
    public void testCombineTriggers(){
        Switch s1 = mock(Switch.class);
        Switch s2 = mock(Switch.class);
        Switch s3 = mock(Switch.class);

        var button1 = Hardware.makeTrigger(s1::isTriggered);
        var button2 = Hardware.makeTrigger(s2::isTriggered);
        var button3 = Hardware.makeTrigger(s3::isTriggered);

        var combined = Hardware.combineTriggers(button1, button2, button3);

        // When all buttons are released, it should be false
        when(s1.isTriggered()).thenReturn(false);
        when(s2.isTriggered()).thenReturn(false);
        when(s3.isTriggered()).thenReturn(false);

        assertFalse(combined.getAsBoolean());

        // When only one button is pressed, it should be false
        when(s1.isTriggered()).thenReturn(true);
        when(s2.isTriggered()).thenReturn(false);
        when(s3.isTriggered()).thenReturn(false);

        assertFalse(combined.getAsBoolean());

        // When only two buttons are pressed, it should be false
        when(s1.isTriggered()).thenReturn(false);
        when(s2.isTriggered()).thenReturn(true);
        when(s3.isTriggered()).thenReturn(true);

        assertFalse(combined.getAsBoolean());

        // When all buttons are pressed, it should be true
        when(s1.isTriggered()).thenReturn(true);
        when(s2.isTriggered()).thenReturn(true);
        when(s3.isTriggered()).thenReturn(true);

        assertTrue(combined.getAsBoolean());
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