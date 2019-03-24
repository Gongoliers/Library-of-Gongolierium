package com.thegongoliers.output.subsystems;

import com.thegongoliers.input.time.Clock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import edu.wpi.first.wpilibj.interfaces.Gyro;

import static org.mockito.Mockito.*;

import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * StabilizedDrivetrainTest
 */
public class StabilizedDrivetrainTest {

    private Drivetrain drivetrain;
    private Gyro gyro;
    private Drivetrain stabilizedDrivetrain;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        gyro = mock(Gyro.class);
        double kp = 0.01;

        stabilizedDrivetrain = new StabilizedDrivetrain(drivetrain, gyro, kp);
    }

    @Test
    public void allowsTurning(){
        stabilizedDrivetrain.arcade(1.0, 0.5);
        verify(drivetrain).arcade(1.0, 0.5);

        stabilizedDrivetrain.arcade(-1.0, -1.0);
        verify(drivetrain).arcade(-1.0, -1.0);
    }

    @Test
    public void correctsDriftingErrors(){
        when(gyro.getAngle()).thenReturn(10.0);
        stabilizedDrivetrain.arcade(1.0, 0);
        verify(drivetrain).arcade(1.0, -0.1);

        when(gyro.getAngle()).thenReturn(-10.0);
        stabilizedDrivetrain.arcade(-1.0, 0);
        verify(drivetrain).arcade(-1.0, 0.1);

        stabilizedDrivetrain.arcade(0, 0);
        verify(drivetrain).arcade(0, 0.1);
    }

    @Test
    public void adaptsToPurposefulHeadingChanges(){
        when(gyro.getAngle()).thenReturn(10.0);
        stabilizedDrivetrain.arcade(1.0, 1.0);

        stabilizedDrivetrain.arcade(1.0, 0);
        verify(drivetrain).arcade(AdditionalMatchers.eq(1.0, 0.001), AdditionalMatchers.eq(0, 0.001));

        when(gyro.getAngle()).thenReturn(12.0);
        stabilizedDrivetrain.arcade(1.0, 0);
        verify(drivetrain).arcade(AdditionalMatchers.eq(1.0, 0.001), AdditionalMatchers.eq(-0.02, 0.001));
    }

    @Test
    public void allowsSettling(){
        Clock clock = mock(Clock.class);
        stabilizedDrivetrain = new StabilizedDrivetrain(drivetrain, gyro, 0.01, 1.0, clock);

        when(clock.getTime()).thenReturn(0.0);
        when(gyro.getAngle()).thenReturn(10.0);
        stabilizedDrivetrain.arcade(1.0, 1.0);

        when(clock.getTime()).thenReturn(0.5);
        when(gyro.getAngle()).thenReturn(20.0);
        stabilizedDrivetrain.arcade(0.0, 0.0);
        verify(drivetrain).arcade(AdditionalMatchers.eq(0.0, 0.001), AdditionalMatchers.eq(0.0, 0.001));

        when(clock.getTime()).thenReturn(1.0);
        when(gyro.getAngle()).thenReturn(20.0);
        stabilizedDrivetrain.arcade(1.0, 0);
        verify(drivetrain).arcade(AdditionalMatchers.eq(1.0, 0.001), AdditionalMatchers.eq(0.0, 0.001));

        when(clock.getTime()).thenReturn(1.0);
        when(gyro.getAngle()).thenReturn(25.0);
        stabilizedDrivetrain.arcade(1.0, 0);
        verify(drivetrain).arcade(AdditionalMatchers.eq(1.0, 0.001), AdditionalMatchers.eq(-0.05, 0.001));


    }

}