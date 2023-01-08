package com.thegongoliers.output.drivetrain;

import com.thegongoliers.utils.IModule;
import com.thegongoliers.utils.Resettable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.AdditionalMatchers;


import static org.mockito.Mockito.*;

import java.util.List;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * ModularDrivetrainTest
 */
public class ModularDrivetrainTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private Clock clock;

    @BeforeEach
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        clock = mock(Clock.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, clock);
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
        ResettableModule module3 = new ResettableModule();
        modularDrivetrain.addModule(module1);
        modularDrivetrain.addModule(module2);
        modularDrivetrain.addModule(module3);
        modularDrivetrain.tank(0, 0.1);
        verifyTank(0.4, 0.5);
        assertFalse(module3.wasReset);
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
    public void canUseAnOverrideModule(){
        ResettableModule module1 = new ResettableModule();
        OverrideModule module2 = new OverrideModule(0.5);
        DriveModule module3 = new AddModule(0.1);
        modularDrivetrain.setModules(module1, module2, module3);
        modularDrivetrain.tank(0, 0);
        assertTrue(module1.wasReset);
        verifyTank(0.6, 0.6);

        module1.wasReset = false;
        modularDrivetrain.setModules(module1, module3, module2);
        modularDrivetrain.tank(0.1, 0.1);
        assertTrue(module1.wasReset);
        verifyTank(0.5, 0.5);

        reset(drivetrain);
        module1.wasReset = false;
        modularDrivetrain.setModules(module2, module1, module3);
        modularDrivetrain.tank(0.2, 0.2);
        assertFalse(module1.wasReset);
        verifyTank(0.6, 0.6);

        module1.wasReset = false;
        module2.isOverriding = false;
        modularDrivetrain.setModules(module2, module1, module3);
        modularDrivetrain.tank(0.2, 0.2);
        assertFalse(module1.wasReset);
        verifyTank(0.3, 0.3);
    }

    @Test
    public void resetsAfterInactive(){
        ResettableModule module = new ResettableModule();
        modularDrivetrain.setModules(module);
        modularDrivetrain.setInactiveResetSeconds(1.0);
        modularDrivetrain.tank(0, 0);
        assertFalse(module.wasReset);

        when(clock.getTime()).thenReturn(0.5);
        modularDrivetrain.tank(0, 0);
        assertFalse(module.wasReset);

        when(clock.getTime()).thenReturn(1.0);
        modularDrivetrain.tank(0, 0);
        assertFalse(module.wasReset);

        when(clock.getTime()).thenReturn(2.0);
        modularDrivetrain.tank(0, 0);
        assertTrue(module.wasReset);
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
        List<IModule<DriveSpeed>> modules = modularDrivetrain.getInstalledModules();
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

    @Test
    public void canGetModuleOfType(){
        MultiplyModule m = new MultiplyModule(0.5);
        modularDrivetrain.addModule(m);
        assertEquals(m, modularDrivetrain.getInstalledModule(MultiplyModule.class));
    }

    private void verifyTank(double left, double right){
        verify(drivetrain).tank(AdditionalMatchers.eq(left, 0.001), AdditionalMatchers.eq(right, 0.001));
    }

    private class MultiplyModule implements DriveModule {

        private double multiplier;

        public MultiplyModule(double multiplier){
            super();
            this.multiplier = multiplier;
        }

        @Override
        public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
            return new DriveSpeed(desiredSpeed.getLeftSpeed() * multiplier, desiredSpeed.getRightSpeed() * multiplier);
        }
    }

    private class AddModule implements DriveModule {

        private double value;

        public AddModule(double value){
            super();
            this.value = value;
        }

        @Override
        public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
            return new DriveSpeed(desiredSpeed.getLeftSpeed() + value, desiredSpeed.getRightSpeed() + value);
        }
    }

    private class ResettableModule implements DriveModule, Resettable {

        public boolean wasReset = false;

        @Override
        public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
            return desiredSpeed;
        }

        @Override
        public void reset() {
            wasReset = true;
        }
    }

    private class OverrideModule implements DriveModule {

        private double value;
        boolean isOverriding = true;

        public OverrideModule(double value){
            super();
            this.value = value;
        }

        @Override
        public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
            if (isOverriding) {
                return new DriveSpeed(value, value);
            }

            return desiredSpeed;
        }

        @Override
        public boolean overridesUser() {
            return isOverriding;
        }
    }

}