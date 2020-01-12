package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import edu.wpi.first.wpilibj.Encoder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.interfaces.Drivetrain;

public class AnchorModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private AnchorModule module;
    private Encoder encoder1, encoder2;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, mock(Clock.class));
        encoder1 = mock(Encoder.class);
        encoder2 = mock(Encoder.class);
        module = new AnchorModule(encoder1, encoder2, 0.1);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void doesNotFortifyWhileTriggerIsOff(){
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
        assertFalse(module.overridesUser());
        module.holdPosition();
        assertTrue(module.overridesUser());

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