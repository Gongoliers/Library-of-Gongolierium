package com.thegongoliers.output.elevator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import edu.wpi.first.wpilibj.SpeedController;

import static org.mockito.Mockito.*;

import com.thegongoliers.input.switches.Switch;

/**
 * LimitedElevatorTest
 */
public class LimitedElevatorTest {

    private LimitedElevator elevator;
    private SpeedController speedController;
    private Switch topLimit, bottomLimit;

    @Before
    public void setup(){
        speedController = mock(SpeedController.class);
        topLimit = mock(Switch.class);
        bottomLimit = mock(Switch.class);
        elevator = new LimitedElevator(speedController, topLimit::isTriggered, bottomLimit::isTriggered);
    }

    @Test
    public void canMoveUpUntilLimit(){
        when(topLimit.isTriggered()).thenReturn(false);

        elevator.up(0.5);
        verify(speedController).set(AdditionalMatchers.eq(0.5, 0.001));

        when(topLimit.isTriggered()).thenReturn(true);
        elevator.up(1);
        verify(speedController).stopMotor();
    }

    @Test
    public void canMoveDownUntilLimit(){
        when(bottomLimit.isTriggered()).thenReturn(false);

        elevator.down(0.5);
        verify(speedController).set(AdditionalMatchers.eq(-0.5, 0.001));

        when(bottomLimit.isTriggered()).thenReturn(true);
        elevator.down(1);
        verify(speedController).stopMotor();
    }

    @Test
    public void canMoveDownWhileAtTop(){
        when(bottomLimit.isTriggered()).thenReturn(false);
        when(topLimit.isTriggered()).thenReturn(true);

        elevator.down(0.5);
        verify(speedController).set(AdditionalMatchers.eq(-0.5, 0.001));
    }
    
    @Test
    public void canMoveUpWhileAtBottom(){
        when(bottomLimit.isTriggered()).thenReturn(true);
        when(topLimit.isTriggered()).thenReturn(false);

        elevator.up(0.5);
        verify(speedController).set(AdditionalMatchers.eq(0.5, 0.001));
    }

    @Test
    public void limitsUpInputTo01(){
        when(bottomLimit.isTriggered()).thenReturn(false);
        when(topLimit.isTriggered()).thenReturn(false);

        elevator.up(1.2);
        verify(speedController).set(AdditionalMatchers.eq(1, 0.001));

        elevator.up(-1.2);
        verify(speedController).set(AdditionalMatchers.eq(0, 0.001));
    }


    @Test
    public void limitsDownInputTo01(){
        when(bottomLimit.isTriggered()).thenReturn(false);
        when(topLimit.isTriggered()).thenReturn(false);

        elevator.down(1.2);
        verify(speedController).set(AdditionalMatchers.eq(-1, 0.001));

        elevator.down(-1.2);
        verify(speedController).set(AdditionalMatchers.eq(0, 0.001));
    }
}