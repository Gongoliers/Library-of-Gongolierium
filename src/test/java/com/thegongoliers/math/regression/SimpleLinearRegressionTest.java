package com.thegongoliers.math.regression;

import com.kylecorry.geometry.Point;
import com.thegongoliers.math.regression.SimpleLinearRegression;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleLinearRegressionTest {

    @Test
    public void test(){
        SimpleLinearRegression regression = new SimpleLinearRegression(new Point(-2, -1, 0),
                new Point(1, 1, 0), new Point(6, 6, 0), new Point(9, 6, 0),
                new Point(11, 11, 0));

        assertEquals(0.93115, regression.getFitStrength(), 0.00001);
        assertEquals(2.9220, regression.predict(3), 0.0001);
    }

}