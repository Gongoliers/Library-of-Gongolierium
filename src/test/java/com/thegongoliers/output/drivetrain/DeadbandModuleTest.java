package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * DeadbandModuleTest
 */
public class DeadbandModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private DriveModule module;
    private InOrder inOrder;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain);
        module = new DeadbandModule(0.1, 0.2);
        modularDrivetrain.addModule(module);
        inOrder = Mockito.inOrder(drivetrain);
    }

    @Test
    public void appliesDeadband(){
        modularDrivetrain.arcade(0.01, 0.01);
        verifyArcade(0, 0);
        
        modularDrivetrain.arcade(-0.01, -0.01);
        verifyArcade(0, 0);

        modularDrivetrain.arcade(-0.1, -0.01);
        verifyArcade(-0.1, 0);

        modularDrivetrain.arcade(0.1, -0.01);
        verifyArcade(0.1, 0);

        modularDrivetrain.arcade(-0.01, -0.2);
        verifyArcade(0, -0.2);

        modularDrivetrain.arcade(0.01, 0.2);
        verifyArcade(0, 0.2);

        modularDrivetrain.arcade(-0.15, 0.25);
        verifyArcade(-0.15, 0.25);
    }

    private void verifyArcade(double speed, double turn){
        inOrder.verify(drivetrain).arcade(AdditionalMatchers.eq(speed, 0.001), AdditionalMatchers.eq(turn, 0.001));
    }

}