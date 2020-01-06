package com.thegongoliers.output.elevator;

import java.util.function.BooleanSupplier;

import com.thegongoliers.math.GMath;
import com.thegongoliers.output.interfaces.Stoppable;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * An elevator with upper and/or lower limits.
 */
public class LimitedElevator implements Stoppable {

    private BooleanSupplier mTopLimit, mBottomLimit;
    private SpeedController mSpeedController;

    public LimitedElevator(SpeedController speedController, BooleanSupplier topLimit, BooleanSupplier bottomLimit){
        mSpeedController = speedController;
        mTopLimit = topLimit;
        mBottomLimit = bottomLimit;
    }

    /**
     * Determines if the elevator is at the top
     * @return True if it is at the top, false otherwise
     */
    public boolean isAtTop(){
        if (mTopLimit == null) return false;
        return mTopLimit.getAsBoolean();
    }

    /**
     * Determines if the elevator is at the bottom
     * @return True if it is at the bottom, false otherwise
     */
    public boolean isAtBottom(){
        if (mBottomLimit == null) return false;
        return mBottomLimit.getAsBoolean();
    }

    /**
     * Move the elevator up, stops at the top limit
     * @param speed the speed to move up
     */
    public void up(double speed){
        if (isAtTop()) stop();

        speed = GMath.clamp01(speed);
        
        mSpeedController.set(speed);
    }

    /**
     * Move the elevator down, stops at the bottom limit
     * @param speed the speed to move down
     */
    public void down(double speed){
        if (isAtBottom()) stop();

        speed = GMath.clamp01(speed);
        
        mSpeedController.set(-speed);
    }

    @Override
    public void stop() {
       mSpeedController.stopMotor();
    }
}