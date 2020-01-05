package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.mockHardware.input.MockClock;
import com.thegongoliers.mockHardware.input.MockSwitch;
import com.thegongoliers.mockHardware.output.MockGearShifter;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * ShifterModuleTest
 */
public class ShifterModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private DriveModule module;
    private MockClock clock;
    private MockGearShifter shifter;
    private MockSwitch downshift, upshift;
    private InOrder inOrder;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        clock = new MockClock();
        clock.setTime(0);
        shifter = new MockGearShifter(3);
        upshift = new MockSwitch();
        downshift = new MockSwitch();
        modularDrivetrain = new ModularDrivetrain(drivetrain, clock);
        module = new ShifterModule(shifter, Hardware.switchToTrigger(downshift), Hardware.switchToTrigger(upshift), 0.0);
        modularDrivetrain.addModule(module);
        inOrder = Mockito.inOrder(drivetrain);
    }

    @Test
    public void togglingUpshiftWithStopTimeWorks(){
        module.setValue(ShifterModule.VALUE_SHIFT_STOP_TIME, 0.1);
        clock.setTime(0);
        upshift();
        assertGear(1);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        clock.setTime(0.05);
        nothing();
        assertGear(1);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        clock.setTime(0.08);
        upshift();
        assertGear(1);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        clock.setTime(0.1);
        upshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 1, 1);
    }

    @Test
    public void upshiftWorksWithTime(){
        module.setValue(ShifterModule.VALUE_SHIFT_STOP_TIME, 0.1);
        clock.setTime(0);
        upshift();
        assertGear(1);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        clock.setTime(0.05);
        upshift();
        assertGear(1);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        clock.setTime(0.1);
        upshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 1, 1);
    }

    @Test
    public void canChangeFromUpshiftToDownshift(){
        module.setValue(ShifterModule.VALUE_SHIFT_STOP_TIME, 0.1);
        clock.setTime(0);
        shifter.setGear(2);
        downshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        clock.setTime(0.05);
        upshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        clock.setTime(0.1);
        upshift();
        assertGear(3);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 1, 1);
    }

    @Test
    public void downshiftWorksWithTime(){
        module.setValue(ShifterModule.VALUE_SHIFT_STOP_TIME, 0.1);
        clock.setTime(0);
        shifter.setGear(3);
        downshift();
        assertGear(3);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        clock.setTime(0.05);
        downshift();
        assertGear(3);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        clock.setTime(0.1);
        downshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 1, 1);
    }

    @Test
    public void onlyShiftsOnPress(){
        upshift();
        assertGear(2);

        upshift();
        assertGear(2);

        nothing();

        upshift();
        assertGear(3);

        downshift();
        assertGear(2);

        downshift();
        assertGear(2);

        nothing();

        downshift();
        assertGear(1);
    }

    @Test
    public void canUpshiftAndDownshift(){
        upshift();
        assertGear(2);

        downshift();
        assertGear(1);
    }

    @Test
    public void canDownshift(){
        shifter.setGear(3);

        downshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 1, 1);

        nothing();
        assertGear(2);

        downshift();
        assertGear(1);

        nothing();
        assertGear(1);

        downshift();
        assertGear(1);
    }

    @Test
    public void canUpshift(){
        shifter.setGear(1);
        upshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 1, 1);

        nothing();
        assertGear(2);

        upshift();
        assertGear(3);

        nothing();
        assertGear(3);

        upshift();
        assertGear(3);
    }

    private void nothing(){
        downshift.setTriggered(false);
        upshift.setTriggered(false);
        modularDrivetrain.tank(1, 1);
    }

    private void downshift(){
        downshift.setTriggered(true);
        upshift.setTriggered(false);
        modularDrivetrain.tank(1, 1);
    }

    private void upshift(){
        downshift.setTriggered(false);
        upshift.setTriggered(true);
        modularDrivetrain.tank(1, 1);
    }

    private void assertGear(int gear){
        assertEquals(gear, shifter.getGear());
    }

}