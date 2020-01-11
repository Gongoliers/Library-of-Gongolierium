package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.input.switches.Switch;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.gears.MockGearShifter;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * ShifterModuleTest
 */
public class ShifterModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private ShifterModule module;
    private Clock clock;
    private MockGearShifter shifter;
    private Switch downshift, upshift;
    private InOrder inOrder;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        clock = mock(Clock.class);
        when(clock.getTime()).thenReturn(0.0);
        shifter = new MockGearShifter(3);
        upshift = mock(Switch.class);
        downshift = mock(Switch.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, clock);
        module = new ShifterModule(shifter, Hardware.switchToTrigger(downshift), Hardware.switchToTrigger(upshift), 0.0);
        modularDrivetrain.addModule(module);
        inOrder = Mockito.inOrder(drivetrain);
    }

    @Test
    public void togglingUpshiftWithStopTimeWorks(){
        module.setShiftStopTime(0.1);
        when(clock.getTime()).thenReturn(0.0);
        upshift();
        assertGear(1);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(0.05);
        nothing();
        assertGear(1);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(0.08);
        upshift();
        assertGear(1);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(0.1);
        upshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 1, 1);
    }

    @Test
    public void upshiftWorksWithTime(){
        module.setShiftStopTime(0.1);
        when(clock.getTime()).thenReturn(0.0);
        upshift();
        assertGear(1);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(0.05);
        upshift();
        assertGear(1);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(0.1);
        upshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 1, 1);
    }

    @Test
    public void canChangeFromUpshiftToDownshift(){
        module.setShiftStopTime(0.1);
        when(clock.getTime()).thenReturn(0.0);
        shifter.setGear(2);
        downshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(0.05);
        upshift();
        assertGear(2);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(0.1);
        upshift();
        assertGear(3);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 1, 1);
    }

    @Test
    public void downshiftWorksWithTime(){
        module.setShiftStopTime(0.1);
        when(clock.getTime()).thenReturn(0.0);
        shifter.setGear(3);
        downshift();
        assertGear(3);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(0.05);
        downshift();
        assertGear(3);
        DrivetrainTestUtils.inorderVerifyTank(inOrder, drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(0.1);
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
        when(downshift.isTriggered()).thenReturn(false);
        when(upshift.isTriggered()).thenReturn(false);
        modularDrivetrain.tank(1, 1);
    }

    private void downshift(){
        when(downshift.isTriggered()).thenReturn(true);
        when(upshift.isTriggered()).thenReturn(false);
        modularDrivetrain.tank(1, 1);
    }

    private void upshift(){
        when(downshift.isTriggered()).thenReturn(false);
        when(upshift.isTriggered()).thenReturn(true);
        modularDrivetrain.tank(1, 1);
    }

    private void assertGear(int gear){
        assertEquals(gear, shifter.getGear());
    }

}