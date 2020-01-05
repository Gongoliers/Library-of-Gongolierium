package com.thegongoliers.output.drivetrain;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.mockHardware.input.MockClock;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.interfaces.Gyro;

import static org.mockito.Mockito.*;

import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * StabilityModuleTest
 */
public class StabilityModuleTest {

    private Drivetrain drivetrain;
    private Gyro gyro;
    private ModularDrivetrain stabilizedDrivetrain;
    private DriveModule stabilityModule;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        gyro = mock(Gyro.class);
        double kp = 0.01;

        stabilizedDrivetrain = new ModularDrivetrain(drivetrain, new MockClock());

        stabilityModule = new StabilityModule(gyro, kp, 0);
        stabilityModule.setValue(StabilityModule.VALUE_CLOCK, new MockClock());
        stabilizedDrivetrain.addModule(stabilityModule);
    }

    @Test
    public void allowsTurning(){
        stabilizedDrivetrain.arcade(1.0, 0.5);
        DrivetrainTestUtils.verifyArcade(drivetrain, 1.0, 0.5);

        stabilizedDrivetrain.arcade(-1.0, -1.0);
        DrivetrainTestUtils.verifyArcade(drivetrain, -1.0, -1.0);
    }

    @Test
    public void correctsDriftingErrors(){
        when(gyro.getAngle()).thenReturn(10.0);
        stabilizedDrivetrain.arcade(1.0, 0);
        DrivetrainTestUtils.verifyArcade(drivetrain, 1.0, -0.1);

        when(gyro.getAngle()).thenReturn(-10.0);
        stabilizedDrivetrain.arcade(-1.0, 0);
        DrivetrainTestUtils.verifyArcade(drivetrain, -1.0, 0.1);

        stabilizedDrivetrain.arcade(0, 0);
        DrivetrainTestUtils.verifyArcade(drivetrain, 0, 0.1);
    }

    @Test
    public void adaptsToPurposefulHeadingChanges(){
        when(gyro.getAngle()).thenReturn(10.0);
        stabilizedDrivetrain.arcade(1.0, 1.0);

        stabilizedDrivetrain.arcade(1.0, 0);
        DrivetrainTestUtils.verifyArcade(drivetrain, 1, 0);

        when(gyro.getAngle()).thenReturn(12.0);
        stabilizedDrivetrain.arcade(1.0, 0);
        DrivetrainTestUtils.verifyArcade(drivetrain, 1, -0.02);
    }

    @Test
    public void allowsSettling(){
        Clock clock = mock(Clock.class);

        stabilityModule.setValue(StabilityModule.VALUE_SETTLING_TIME, 1.0);
        stabilityModule.setValue(StabilityModule.VALUE_CLOCK, clock);

        when(clock.getTime()).thenReturn(0.0);
        when(gyro.getAngle()).thenReturn(10.0);
        stabilizedDrivetrain.arcade(1.0, 1.0);

        when(clock.getTime()).thenReturn(0.5);
        when(gyro.getAngle()).thenReturn(20.0);
        stabilizedDrivetrain.arcade(0.0, 0.0);
        DrivetrainTestUtils.verifyArcade(drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(1.0);
        when(gyro.getAngle()).thenReturn(20.0);
        stabilizedDrivetrain.arcade(1.0, 0);
        DrivetrainTestUtils.verifyArcade(drivetrain, 1.0, 0.0);

        when(clock.getTime()).thenReturn(1.0);
        when(gyro.getAngle()).thenReturn(25.0);
        stabilizedDrivetrain.arcade(1.0, 0);
        DrivetrainTestUtils.verifyArcade(drivetrain, 1, -0.05);
    }

}