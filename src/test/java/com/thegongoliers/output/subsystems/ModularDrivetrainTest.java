package com.thegongoliers.output.subsystems;

import com.thegongoliers.input.time.Clock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import edu.wpi.first.wpilibj.interfaces.Gyro;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.thegongoliers.output.drivemodules.BaseDriveModule;
import com.thegongoliers.output.drivemodules.DriveModule;
import com.thegongoliers.output.drivemodules.DriveValue;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * ModularDrivetrainTest
 */
public class ModularDrivetrainTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain);
    }

    @Test
    public void worksWithoutModules(){
        modularDrivetrain.arcade(1, 0.5);
        verifyArcade(1, 0.5);
    }

    @Test
    public void canAddASingleModule(){
        DriveModule module = new AddModule(0.1);
        modularDrivetrain.addModule(module);
        modularDrivetrain.arcade(0, 0.1);
        verifyArcade(0.1, 0.2);
    }

    @Test
    public void canRemoveASingleModule(){
        DriveModule module = new AddModule(0.1);
        modularDrivetrain.addModule(module);
        modularDrivetrain.removeModule(module);
        modularDrivetrain.arcade(0, 0.1);
        verifyArcade(0, 0.1);
    }

    @Test
    public void canAddMultipleModules(){
        DriveModule module1 = new AddModule(0.1);
        DriveModule module2 = new AddModule(0.3);
        modularDrivetrain.addModule(module1);
        modularDrivetrain.addModule(module2);
        modularDrivetrain.arcade(0, 0.1);
        verifyArcade(0.4, 0.5);
    }

    @Test
    public void modulesFollowOrderProperty(){
        DriveModule module1 = new MultiplyModule(2);
        DriveModule module2 = new AddModule(0.1);
        modularDrivetrain.addModule(module2);
        modularDrivetrain.addModule(module1);
        modularDrivetrain.arcade(0, 0.1);
        verifyArcade(0.1, 0.3);
    }

    @Test
    public void canRemoveMultipleModules(){
        DriveModule module1 = new MultiplyModule(2);
        DriveModule module2 = new AddModule(0.1);
        modularDrivetrain.addModule(module1);
        modularDrivetrain.addModule(module2);
        modularDrivetrain.removeModule(module2);
        modularDrivetrain.arcade(0, 0.1);
        verifyArcade(0, 0.2);
        modularDrivetrain.removeModule(module1);
        modularDrivetrain.arcade(0, 0.1);
        verifyArcade(0, 0.1);
    }

    private void verifyArcade(double speed, double turn){
        verify(drivetrain).arcade(AdditionalMatchers.eq(speed, 0.001), AdditionalMatchers.eq(turn, 0.001));
    }

    private class MultiplyModule extends BaseDriveModule {

        private double multiplier;

        public MultiplyModule(double multiplier){
            super();
            this.multiplier = multiplier;
        }

        @Override
        public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed) {
            return new DriveValue(desiredSpeed.getForwardSpeed() * multiplier, desiredSpeed.getTurnSpeed() * multiplier);
        }

        @Override
        public int getOrder() {
            return 0;
        }

    }

    private class AddModule extends BaseDriveModule {

        private double value;

        public AddModule(double value){
            super();
            this.value = value;
        }

        @Override
        public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed) {
            return new DriveValue(desiredSpeed.getForwardSpeed() + value, desiredSpeed.getTurnSpeed() + value);
        }

        @Override
        public int getOrder() {
            return 1;
        }

    }

}