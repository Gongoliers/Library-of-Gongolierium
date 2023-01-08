package com.thegongoliers.output.drivetrain;

import com.thegongoliers.input.time.Clock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj.interfaces.Gyro;

import static org.mockito.Mockito.*;

import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * StabilityModuleTest
 */
public class GyroStabilityModuleTest {

    private Drivetrain drivetrain;
    private Gyro gyro;
    private ModularDrivetrain stabilizedDrivetrain;
    private GyroStabilityModule gyroStabilityModule;

    @BeforeEach
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        gyro = mock(Gyro.class);
        double kp = 0.01;

        stabilizedDrivetrain = new ModularDrivetrain(drivetrain, mock(Clock.class));

        gyroStabilityModule = new GyroStabilityModule(gyro, kp, 0);
        gyroStabilityModule.setClock(mock(Clock.class));
        stabilizedDrivetrain.addModule(gyroStabilityModule);
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

        gyroStabilityModule.setSettlingTime(1);
        gyroStabilityModule.setClock(clock);

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

    @Test
    public void canResetHeading(){
        when(gyro.getAngle()).thenReturn(10.0);
        stabilizedDrivetrain.arcade(1.0, 1.0);

        when(gyro.getAngle()).thenReturn(100.0);
        gyroStabilityModule.reset();
        stabilizedDrivetrain.arcade(0.9, 0.9);
        DrivetrainTestUtils.verifyArcade(drivetrain, 0.9, 0.9);
    }

}