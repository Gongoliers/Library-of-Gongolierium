package com.thegongoliers.output.elevator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import edu.wpi.first.wpilibj.SpeedController;

import static org.mockito.Mockito.*;

import com.thegongoliers.mockHardware.input.MockSwitch;

/**
 * LimitedElevatorTest
 */
public class LimitedElevatorTest {

    private LimitedElevator elevator;
    private SpeedController speedController;
    private MockSwitch topLimit, bottomLimit;

    @Before
    public void setup(){
        speedController = mock(SpeedController.class);
        topLimit = new MockSwitch();
        bottomLimit = new MockSwitch();
        elevator = new LimitedElevator(speedController, topLimit::isTriggered, bottomLimit::isTriggered);
    }

    @Test
    public void canMoveUpUntilLimit(){
        topLimit.setTriggered(false);

        elevator.up(0.5);
        verify(speedController).set(AdditionalMatchers.eq(0.5, 0.001));

        topLimit.setTriggered(true);
        elevator.up(1);
        verify(speedController).stopMotor();
    }

    @Test
    public void canMoveDownUntilLimit(){
        bottomLimit.setTriggered(false);

        elevator.down(0.5);
        verify(speedController).set(AdditionalMatchers.eq(-0.5, 0.001));

        bottomLimit.setTriggered(true);
        elevator.down(1);
        verify(speedController).stopMotor();
    }

    @Test
    public void canMoveDownWhileAtTop(){
        bottomLimit.setTriggered(false);
        topLimit.setTriggered(true);

        elevator.down(0.5);
        verify(speedController).set(AdditionalMatchers.eq(-0.5, 0.001));
    }
    
    @Test
    public void canMoveUpWhileAtBottom(){
        bottomLimit.setTriggered(true);
        topLimit.setTriggered(false);

        elevator.up(0.5);
        verify(speedController).set(AdditionalMatchers.eq(0.5, 0.001));
    }

    @Test
    public void limitsUpInputTo01(){
        bottomLimit.setTriggered(false);
        topLimit.setTriggered(false);

        elevator.up(1.2);
        verify(speedController).set(AdditionalMatchers.eq(1, 0.001));

        elevator.up(-1.2);
        verify(speedController).set(AdditionalMatchers.eq(0, 0.001));
    }


    @Test
    public void limitsDownInputTo01(){
        bottomLimit.setTriggered(false);
        topLimit.setTriggered(false);

        elevator.down(1.2);
        verify(speedController).set(AdditionalMatchers.eq(-1, 0.001));

        elevator.down(-1.2);
        verify(speedController).set(AdditionalMatchers.eq(0, 0.001));
    }
}