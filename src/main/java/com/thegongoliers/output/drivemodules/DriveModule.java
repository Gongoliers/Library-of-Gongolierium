package com.thegongoliers.output.drivemodules;

import java.util.Set;

public interface DriveModule {

    Object getValue(String name);

    void setValue(String name, Object value);

    Set<String> getValueNames();

    DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed);

    int getOrder();

}