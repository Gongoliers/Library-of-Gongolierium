package com.thegongoliers.math.regression;

public interface Regression {

    /**
     * Predict a value given one or more inputs.
     * @param input1 The first input.
     * @param input The rest of the input.
     * @return The prediction.
     */
    double predict(double input1, double... input);

}
