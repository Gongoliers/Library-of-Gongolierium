package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.buttons.Trigger;

import static org.mockito.Mockito.*;

import java.util.Arrays;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.mockHardware.input.MockSwitch;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * AnchorModuleTest
 */
public class AnchorModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private DriveModule module;
    private Encoder encoder1, encoder2;

    private MockSwitch on;


    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain);
        encoder1 = mock(Encoder.class);
        encoder2 = mock(Encoder.class);
        on = new MockSwitch();
        Trigger trigger = Hardware.switchToTrigger(on);
        module = new AnchorModule(encoder1, encoder2, 0.1, trigger);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void doesNotFortifyWhileTriggerIsOff(){
        on.setTriggered(false);

        when(encoder1.getDistance()).thenReturn(0.0);
        when(encoder2.getDistance()).thenReturn(0.0);
        modularDrivetrain.tank(1, 1);
        verifyTank(1, 1);

        when(encoder1.getDistance()).thenReturn(1.0);
        when(encoder2.getDistance()).thenReturn(1.0);
        modularDrivetrain.tank(0, 1);
        verifyTank(0, 1);
    }

    @Test
    public void fortifyWhileTriggerIsOn(){
        on.setTriggered(true);

        when(encoder1.getDistance()).thenReturn(0.0);
        when(encoder2.getDistance()).thenReturn(0.0);
        modularDrivetrain.tank(1, 1);
        verifyTank(0, 0);

        when(encoder1.getDistance()).thenReturn(1.0);
        when(encoder2.getDistance()).thenReturn(1.0);
        modularDrivetrain.tank(1, 1);
        verifyTank(-0.1, -0.1);

        when(encoder1.getDistance()).thenReturn(0.0);
        when(encoder2.getDistance()).thenReturn(1.0);
        modularDrivetrain.tank(1, 1);
        verifyTank(0, -0.1);
    }

    

    private void verifyTank(double left, double right){
        verify(drivetrain).tank(AdditionalMatchers.eq(left, 0.001), AdditionalMatchers.eq(right, 0.001));
    }

}