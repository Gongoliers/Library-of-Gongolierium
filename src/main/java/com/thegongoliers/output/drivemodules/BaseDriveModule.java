package com.thegongoliers.output.drivemodules;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A base drive module from which new drive modules should extend. Includes support for the values map.
 */
public abstract class BaseDriveModule implements DriveModule {

    protected Map<String, Object> values;

    public BaseDriveModule(){
        values = new HashMap<>();
    }

    @Override
    public abstract DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed);

    @Override
    public abstract int getOrder();

    @Override
    public Object getValue(String name) {
        return values.get(name);
    }

    @Override
    public void setValue(String name, Object value) {
        if (values.containsKey(name)){
            values.put(name, value);
        }
    }

    @Override
    public Set<String> getValueNames(){
        return values.keySet();
    }

}