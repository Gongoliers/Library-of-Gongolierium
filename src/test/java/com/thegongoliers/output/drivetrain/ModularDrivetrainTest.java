package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

import com.thegongoliers.mockHardware.input.MockClock;
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
        modularDrivetrain = new ModularDrivetrain(drivetrain, new MockClock());
    }

    @Test
    public void worksWithoutModules(){
        modularDrivetrain.tank(1, 0.5);
        verifyTank(1, 0.5);
    }

    @Test
    public void canAddASingleModule(){
        DriveModule module = new AddModule(0.1);
        modularDrivetrain.addModule(module);
        modularDrivetrain.tank(0, 0.1);
        verifyTank(0.1, 0.2);
    }

    @Test
    public void canRemoveASingleModule(){
        DriveModule module = new AddModule(0.1);
        modularDrivetrain.addModule(module);
        modularDrivetrain.removeModule(module);
        modularDrivetrain.tank(0, 0.1);
        verifyTank(0, 0.1);
    }

    @Test
    public void canAddMultipleModules(){
        DriveModule module1 = new AddModule(0.1);
        DriveModule module2 = new AddModule(0.3);
        modularDrivetrain.addModule(module1);
        modularDrivetrain.addModule(module2);
        modularDrivetrain.tank(0, 0.1);
        verifyTank(0.4, 0.5);
    }

    @Test
    public void followsOrderOfAdded(){
        DriveModule module1 = new MultiplyModule(2);
        DriveModule module2 = new AddModule(0.1);
        modularDrivetrain.setModules(module2, module1);
        modularDrivetrain.tank(0, 0.1);
        verifyTank(0.2, 0.4);
    }

    @Test
    public void canRemoveMultipleModules(){
        DriveModule module1 = new MultiplyModule(2);
        DriveModule module2 = new AddModule(0.1);
        modularDrivetrain.setModules(module1, module2);
        modularDrivetrain.removeModule(module2);
        modularDrivetrain.tank(0, 0.1);
        verifyTank(0, 0.2);
        modularDrivetrain.removeModule(module1);
        modularDrivetrain.tank(0, 0.1);
        verifyTank(0, 0.1);
    }

    @Test
    public void canGetInstalledModules(){
        DriveModule module1 = new MultiplyModule(2);
        DriveModule module2 = new AddModule(0.1);
        modularDrivetrain.addModule(module2);
        modularDrivetrain.addModule(module1);
        List<DriveModule> modules = modularDrivetrain.getInstalledModules();
        assertEquals(2, modules.size());
        assertTrue(modules.contains(module1));
        assertTrue(modules.contains(module2));
    }

    @Test
    public void worksWithArcade(){
        double speed = 1;
        double turn = 0.5;

        DriveSpeed asTank = DriveSpeed.fromArcade(speed, turn);
        modularDrivetrain.addModule(new MultiplyModule(0.5));
        modularDrivetrain.arcade(speed, turn);
        verifyTank(asTank.getLeftSpeed() * 0.5, asTank.getRightSpeed() * 0.5);
    }

    private void verifyTank(double left, double right){
        verify(drivetrain).tank(AdditionalMatchers.eq(left, 0.001), AdditionalMatchers.eq(right, 0.001));
    }

    private class MultiplyModule extends BaseDriveModule {

        private double multiplier;

        public MultiplyModule(double multiplier){
            super();
            this.multiplier = multiplier;
        }

        @Override
        public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
            return new DriveSpeed(desiredSpeed.getLeftSpeed() * multiplier, desiredSpeed.getRightSpeed() * multiplier);
        }

        @Override
        public String getName() {
            return "Multiply";
        }

    }

    private class AddModule extends BaseDriveModule {

        private double value;

        public AddModule(double value){
            super();
            this.value = value;
        }

        @Override
        public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
            return new DriveSpeed(desiredSpeed.getLeftSpeed() + value, desiredSpeed.getRightSpeed() + value);
        }

        @Override
        public String getName() {
            return "Add";
        }

    }

}