package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

public class DeadbandModule implements DriveModule {

    private final double mForward;
    private final double mTurn;

    public DeadbandModule(double forward, double turn) {
        mForward = forward;
        mTurn = turn;
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        var forward = GMath.deadband(desiredSpeed.forward(), mForward);
        var turn = GMath.deadband(desiredSpeed.turn(), mTurn);
        return DriveSpeed.fromArcade(forward, turn);
    }
}
