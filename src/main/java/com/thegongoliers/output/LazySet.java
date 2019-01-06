package com.thegongoliers.output;

import java.util.function.Consumer;

/**
 * A class to set a value only when it is different.
 * @param <T> The type of the value.
 */
public class LazySet<T> {

    private T lastValue = null;
    private Consumer<T> setFunction;

    /**
     * Creates an instance of LazySet.
     * @param setFunction The function to call to set the value.
     */
    public LazySet(Consumer<T> setFunction) {
        this.setFunction = setFunction;
    }

    /**
     * Sets a value only if it is different from the last call to set.
     * @param newValue The new value.
     * @return True if the value was different, and therefore set.
     */
    public boolean set(T newValue){
        if (newValue != null && !newValue.equals(lastValue)){
            setFunction.accept(newValue);
            lastValue = newValue;
            return true;
        }
        return false;
    }

}
