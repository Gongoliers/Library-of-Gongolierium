package com.thegongoliers.output.drivetrain;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DriveSpeedTest {

    @Test
    public void fromArcade() {
        var cases = List.of(
            List.of(DriveSpeed.fromArcade(0.5, 0.1), new DriveSpeed(0.5, 0.4)),
            List.of(DriveSpeed.fromArcade(0.5, -0.1), new DriveSpeed(0.4, 0.5)),
            List.of(DriveSpeed.fromArcade(1.0, 0.0), new DriveSpeed(1.0, 1.0)),
            List.of(DriveSpeed.fromArcade(-1.0, 0.0), new DriveSpeed(-1.0, -1.0)),
            List.of(DriveSpeed.fromArcade(0.0, 1.0), new DriveSpeed(1.0, -1.0)),
            List.of(DriveSpeed.fromArcade(0.0, -1.0), new DriveSpeed(-1.0, 1.0))
        );

        for (var c: cases) {
            assertEquals(c.get(1).getLeftSpeed(), c.get(0).getLeftSpeed(), 0.001);
            assertEquals(c.get(1).getRightSpeed(), c.get(0).getRightSpeed(), 0.001);
        }
    }

    @Test
    public void toArcade() {
        var cases = List.of(
                List.of(0.5, 0.1),
                List.of(-0.5, 0.1),
                List.of(0.5, -0.1),
                List.of(-0.5, -0.1),
                List.of(1.0, 0.0),
                List.of(-1.0, 0.0),
                List.of(0.0, 1.0),
                List.of(0.0, -1.0),
                List.of(0.5, -1.0),
                List.of(0.0, -0.5),
                List.of(0.0, 0.5)
        );

        for (var c: cases) {
            var tank = DriveSpeed.fromArcade(c.get(0), c.get(1));
            assertEquals(c.get(0), tank.forward(), 0.001);
            assertEquals(c.get(1), tank.turn(), 0.001);
        }
    }
}