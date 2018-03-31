package com.thegongoliers.output;

import com.thegongoliers.input.MockVoltageSensor;
import com.thegongoliers.output.interfaces.IMotor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MotorTest {

    private Motor motor;
    private MockSpeedController speedController;
    private MockVoltageSensor voltageSensor;


    @Before
    public void setup(){
        speedController = new MockSpeedController();
        voltageSensor = new MockVoltageSensor();
        motor = new Motor(speedController, voltageSensor);
    }

    @Test
    public void pwm(){
        assertEquals(motor.getPWM(), 0, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Stopped);

        // Forward
        motor.setPWM(1, IMotor.Direction.Forward);
        assertEquals(motor.getPWM(), 1, 0.0);
        assertEquals(speedController.get(), 1, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Forward);

        assertEquals(motor.getControlType(), Motor.ControlType.PWM);

        motor.setPWM(0.5, IMotor.Direction.Forward);
        assertEquals(motor.getPWM(), 0.5, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Forward);

        motor.setPWM(0, IMotor.Direction.Forward);
        assertEquals(motor.getPWM(), 0, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Forward);

        motor.setPWM(-1, IMotor.Direction.Forward);
        assertEquals(motor.getPWM(), 1, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Forward);


        // Backward
        motor.setPWM(1, IMotor.Direction.Backward);
        assertEquals(motor.getPWM(), 1, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Backward);

        motor.setPWM(0.5, IMotor.Direction.Backward);
        assertEquals(motor.getPWM(), 0.5, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Backward);


        motor.setPWM(0, IMotor.Direction.Backward);
        assertEquals(motor.getPWM(), 0, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Backward);


        motor.setPWM(-1, IMotor.Direction.Backward);
        assertEquals(motor.getPWM(), 1, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Backward);

        // Stopped
        motor.setPWM(1, IMotor.Direction.Stopped);
        assertEquals(motor.getPWM(), 0, 0.0);

        // Shortcuts
        motor.setPWM(-0.75);
        assertEquals(motor.getPWM(), 0.75, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Backward);

        motor.setPWM(0.6);
        assertEquals(motor.getPWM(), 0.6, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Forward);

        motor.setPWM(0);
        assertEquals(motor.getPWM(), 0, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Stopped);

        // Out of bounds
        motor.setPWM(10);
        assertEquals(motor.getPWM(), 1, 0.0);

        motor.setPWM(-10);
        assertEquals(motor.getPWM(), 1, 0.0);
    }


    @Test
    public void voltage(){
        assertEquals(motor.getVoltage(), 0, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Stopped);

        voltageSensor.setVoltage(10);

        // Forward
        motor.setVoltage(10, IMotor.Direction.Forward);
        assertEquals(motor.getVoltage(), 10, 0.0);
        assertEquals(motor.getPWM(), 1, 0.0);
        assertEquals(speedController.get(), 1, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Forward);
        assertEquals(motor.getControlType(), Motor.ControlType.Voltage);

        motor.setVoltage(5, IMotor.Direction.Forward);
        assertEquals(motor.getVoltage(), 5, 0.0);
        assertEquals(motor.getPWM(), 0.5, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Forward);

        motor.setVoltage(0, IMotor.Direction.Forward);
        assertEquals(motor.getVoltage(), 0, 0.0);
        assertEquals(motor.getPWM(), 0, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Forward);

        // Backward
        motor.setVoltage(10, IMotor.Direction.Backward);
        assertEquals(motor.getVoltage(), 10, 0.0);
        assertEquals(motor.getPWM(), 1, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Backward);
        assertEquals(motor.getControlType(), Motor.ControlType.Voltage);

        motor.setVoltage(5, IMotor.Direction.Backward);
        assertEquals(motor.getVoltage(), 5, 0.0);
        assertEquals(motor.getPWM(), 0.5, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Backward);

        motor.setVoltage(0, IMotor.Direction.Backward);
        assertEquals(motor.getVoltage(), 0, 0.0);
        assertEquals(motor.getPWM(), 0, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Backward);

        // Stopped
        motor.setVoltage(10, IMotor.Direction.Stopped);
        assertEquals(motor.getVoltage(), 0, 0.0);
        assertEquals(motor.getPWM(), 0, 00.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Stopped);
        assertEquals(motor.getControlType(), Motor.ControlType.Voltage);

        motor.setVoltage(5, IMotor.Direction.Stopped);
        assertEquals(motor.getVoltage(), 0, 0.0);
        assertEquals(motor.getPWM(), 0, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Stopped);

        motor.setVoltage(0, IMotor.Direction.Stopped);
        assertEquals(motor.getVoltage(), 0, 0.0);
        assertEquals(motor.getPWM(), 0, 0.0);
        assertEquals(motor.getDirection(), IMotor.Direction.Stopped);

        // Changing voltage
        voltageSensor.setVoltage(12);

        motor.setVoltage(6, IMotor.Direction.Forward);
        assertEquals(motor.getVoltage(), 6, 0);
        assertEquals(motor.getPWM(), 0.5, 0);

        // Shortcuts
        motor.setVoltage(6);
        assertEquals(motor.getVoltage(), 6, 0);
        assertEquals(motor.getDirection(), IMotor.Direction.Forward);

        motor.setVoltage(-6);
        assertEquals(motor.getVoltage(), 6, 0);
        assertEquals(motor.getDirection(), IMotor.Direction.Backward);

        motor.setVoltage(0);
        assertEquals(motor.getVoltage(), 0, 0);
        assertEquals(motor.getDirection(), IMotor.Direction.Stopped);
    }

    @Test
    public void invert(){
        motor.setInverted(true);
        assertTrue(motor.isInverted());
        assertTrue(speedController.getInverted());

        motor.setInverted(false);
        assertFalse(motor.isInverted());
        assertFalse(speedController.getInverted());
    }

}