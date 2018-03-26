package com.thegongoliers.math.regression;

import com.kylecorry.geometry.Point;

public class SimpleLinearRegression implements Regression {

    private double r2 = 0;
    private double b0, b1;

    /**
     * Create a simple linear regression from the given points.
     * @param points The points to fit the regression to.
     */
    public SimpleLinearRegression(Point... points){
        fit(points);
    }

    /**
     * Fit the regression to the given points.
     * @param points The points to fit to.
     */
    private void fit(Point... points){
        if(points.length == 0){
            r2 = 0;
            b0 = 0;
            b1 = 0;
            return;
        }
        double xBar = 0, yBar = 0;
        for (Point p: points) {
            xBar += p.x;
            yBar += p.y;
        }
        xBar /= points.length;
        yBar /= points.length;

        double ssxx = 0, ssxy = 0, ssto = 0;

        for (Point p: points){
            ssxx += Math.pow(p.x - xBar, 2);
            ssxy += (p.x - xBar) * (p.y - yBar);
            ssto += Math.pow(p.y - yBar, 2);
        }

        b1 = ssxy / ssxx;
        b0 = yBar - xBar * b1;

        double sse = 0;

        for(Point p: points){
            sse += Math.pow(p.y - predict(p.x), 2);
        }

        r2 = 1 - sse / ssto;
    }

    /**
     * Predict the value at the given input.
     * @param input1 The first input.
     * @param input Not used in SLR.
     * @return The prediction at input1.
     */
    @Override
    public double predict(double input1, double... input) {
        return b0 + b1 * input1;
    }

    /**
     * Gets the strength of the fit from [0, 1] (r^2), where 1 is a perfect fit and 0 is no fit.
     * @return The strength of the fit.
     */
    public double getFitStrength(){
        return r2;
    }
}
