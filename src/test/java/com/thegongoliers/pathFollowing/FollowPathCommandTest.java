package com.thegongoliers.pathFollowing;

import org.junit.Test;

import static org.junit.Assert.*;

public class FollowPathCommandTest {

    @Test
    public void calculatesRotationBetweenWaypoints(){

        // Works on axis
        double angle = FollowPathCommand.calculateAngle(0, 0, 1, 0);
        assertEquals(0, angle, 0.0001);

        angle = FollowPathCommand.calculateAngle(0, 0, -1, 0);
        assertEquals(180, angle, 0.0001);

        angle = FollowPathCommand.calculateAngle(0, 0, 0, 1);
        assertEquals(90, angle, 0.0001);

        angle = FollowPathCommand.calculateAngle(0, 0, 0, -1);
        assertEquals(-90, angle, 0.0001);

        // Works on non-origin points
        angle = FollowPathCommand.calculateAngle(1, 2, 1, 1);
        assertEquals(-90, angle, 0.0001);

        angle = FollowPathCommand.calculateAngle(1, 2, 2, 3);
        assertEquals(45, angle, 0.0001);

        // Works for the same point
        angle = FollowPathCommand.calculateAngle(1, 1, 1, 1);
        assertEquals(0, angle, 0.0001);

    }

    @Test
    public void calculatesDistanceBetweenPoints(){
        // Works on axis
        double distance = FollowPathCommand.calculateDistance(0, 0, 1, 0);
        assertEquals(1, distance, 0.0001);

        distance = FollowPathCommand.calculateDistance(0, 0, -2, 0);
        assertEquals(2, distance, 0.0001);

        distance = FollowPathCommand.calculateDistance(0, 0, 0, 1);
        assertEquals(1, distance, 0.0001);

        distance = FollowPathCommand.calculateDistance(0, 0, 0, -2);
        assertEquals(2, distance, 0.0001);

        // Works on other points
        distance = FollowPathCommand.calculateDistance(1, 2, 1, -2);
        assertEquals(4, distance, 0.0001);

        distance = FollowPathCommand.calculateDistance(1, 1, 2, 2);
        assertEquals(Math.sqrt(2), distance, 0.0001);

    }

}