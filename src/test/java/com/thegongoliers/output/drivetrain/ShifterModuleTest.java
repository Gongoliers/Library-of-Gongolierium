package com.thegongoliers.output.drivetrain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

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
    private InOrder inOrder;

    @BeforeEach
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        clock = mock(Clock.class);
        when(clock.getTime()).thenReturn(0.0);
        shifter = new MockGearShifter(3);
        modularDrivetrain = new ModularDrivetrain(drivetrain, clock);
        module = new ShifterModule(shifter, 0.0);
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
        modularDrivetrain.tank(1, 1);
    }

    private void downshift(){
        module.downshift();
        modularDrivetrain.tank(1, 1);
    }

    private void upshift(){
        module.upshift();
        modularDrivetrain.tank(1, 1);
    }

    private void assertGear(int gear){
        assertEquals(gear, shifter.getGear());
    }

}