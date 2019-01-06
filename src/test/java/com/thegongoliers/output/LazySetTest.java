package com.thegongoliers.output;

import org.junit.Test;

import static org.junit.Assert.*;

public class LazySetTest {

    @Test
    public void testSet(){
        CounterSet counterSet = new CounterSet();
        LazySet<Double> lazySet = new LazySet<>(counterSet::set);

        lazySet.set(0.0);
        assertEquals(0.0, counterSet.get(), 0.0001);
        assertEquals(1, counterSet.getCount());

        lazySet.set(1.0);
        assertEquals(1.0, counterSet.get(), 0.0001);
        assertEquals(2, counterSet.getCount());


        lazySet.set(1.0);
        assertEquals(1.0, counterSet.get(), 0.0001);
        assertEquals(2, counterSet.getCount());

        lazySet.set(2.0);
        assertEquals(2.0, counterSet.get(), 0.0001);
        assertEquals(3, counterSet.getCount());
    }

    private class CounterSet {

        private double d = 0.0;
        private int count = 0;

        void set(double d){
            count++;
            this.d = d;
        }

        double get(){
            return d;
        }

        int getCount(){
            return count;
        }
    }

}