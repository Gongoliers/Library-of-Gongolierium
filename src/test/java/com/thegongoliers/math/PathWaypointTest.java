package com.thegongoliers.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class PathWaypointTest {

    @Test
    public void test(){
        PathWaypoint pathWaypoint = new PathWaypoint(1, 2, 3);

        assertEquals(1, pathWaypoint.getX(), 0.0001);
        assertEquals(2, pathWaypoint.getY(), 0.0001);
        assertEquals(3, pathWaypoint.getHeading(), 0.0001);


        pathWaypoint.setX(4);
        pathWaypoint.setY(5);
        pathWaypoint.setHeading(6);

        assertEquals(4, pathWaypoint.getX(), 0.0001);
        assertEquals(5, pathWaypoint.getY(), 0.0001);
        assertEquals(6, pathWaypoint.getHeading(), 0.0001);
    }

}