package com.thegongoliers.hardware;

import com.thegongoliers.input.switches.ResettableSwitch;
import com.thegongoliers.input.switches.Switch;
import com.thegongoliers.mockHardware.input.MockGyro;
import com.thegongoliers.mockHardware.input.MockPotentiometer;
import com.thegongoliers.mockHardware.input.MockSwitch;
import com.thegongoliers.output.interfaces.Drivetrain;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
    public void testCombineButtons(){
        MockSwitch s1 = new MockSwitch();
        MockSwitch s2 = new MockSwitch();
        MockSwitch s3 = new MockSwitch();

        Button button1 = Hardware.makeButton(s1::isTriggered);
        Button button2 = Hardware.makeButton(s2::isTriggered);
        Button button3 = Hardware.makeButton(s3::isTriggered);

        Button combined = Hardware.combineButtons(button1, button2, button3);

        // When all buttons are released, it should be false
        s1.setTriggered(false);
        s2.setTriggered(false);
        s3.setTriggered(false);

        assertFalse(combined.get());

        // When only one button is pressed, it should be false
        s1.setTriggered(true);
        s2.setTriggered(false);
        s3.setTriggered(false);

        assertFalse(combined.get());

        // When only two buttons are pressed, it should be false
        s1.setTriggered(false);
        s2.setTriggered(true);
        s3.setTriggered(true);

        assertFalse(combined.get());

        // When all buttons are pressed, it should be true
        s1.setTriggered(true);
        s2.setTriggered(true);
        s3.setTriggered(true);

        assertTrue(combined.get());

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
    public void testMakeSwitch(){
        MockSwitch mockSwitch = new MockSwitch();

        Switch s = Hardware.makeSwitch(mockSwitch::isTriggered);

        assertNotNull(s);

        mockSwitch.setTriggered(false);
        assertFalse(s.isTriggered());

        mockSwitch.setTriggered(true);
        assertTrue(s.isTriggered());
    }

    @Test
    public void testMakeResettableSwitchFromSwitch(){
        MockSwitch mockSwitch = new MockSwitch();

        ResettableSwitch s = Hardware.makeResettableSwitch(mockSwitch);
        assertNotNull(s);

        mockSwitch.setTriggered(false);
        assertFalse(s.isTriggered());

        mockSwitch.setTriggered(true);
        assertTrue(s.isTriggered());

        mockSwitch.setTriggered(false);
        assertTrue(s.isTriggered());

        s.reset();
        assertFalse(s.isTriggered());
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


    @Test
    public void testInvertPotentiometer(){
        MockPotentiometer potentiometer = new MockPotentiometer();
        Potentiometer p = Hardware.invertPotentiometer(potentiometer);

        // Goes from 1000 -> 900, but it should be 0 -> 100

        double rawAngle = 1000;
        double offset = 1000;

        assertNotNull(p);

        potentiometer.setAngle(rawAngle - offset);
        assertEquals(0, p.get(), 0.001);

        rawAngle = 900;
        potentiometer.setAngle(rawAngle - offset);
        assertEquals(100, p.get(), 0.001);

        rawAngle = 1100;
        potentiometer.setAngle(rawAngle - offset);
        assertEquals(-100, p.get(), 0.001);

    }

    @Test
    public void testCreatingDrivetrain(){
        DifferentialDrive differentialDrive = mock(DifferentialDrive.class);
        Drivetrain drivetrain = Hardware.createDrivetrain(differentialDrive);

        drivetrain.stop();
        verify(differentialDrive).stopMotor();

        drivetrain.arcade(1.0, 0.5);
        verify(differentialDrive).arcadeDrive(1.0, 0.5);

        drivetrain.tank(-1.0, 1.0);
        verify(differentialDrive).tankDrive(-1.0, 1.0);

    }

    

}