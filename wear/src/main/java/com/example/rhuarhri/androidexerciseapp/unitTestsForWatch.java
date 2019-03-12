package com.example.rhuarhri.androidexerciseapp;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class unitTestsForWatch {



    @Test
    public void PerformanceBelowMinimumValue()
    {
         WatchActivity testWatch = new WatchActivity();
        int result = testWatch.calculatePerformance(79, 3);



         int expected = 0;

         assertEquals(expected, result);
    }

    @Test
    public void PerformanceGoodButNotMoving()
    {
        WatchActivity testWatch = new WatchActivity();
        int result = testWatch.calculatePerformance(120, 0);



        int expected = 0;

        assertEquals(expected, result);
    }

    @Test
    public void PerformanceHigh()
    {
        WatchActivity testWatch = new WatchActivity();
        int result = testWatch.calculatePerformance(241, 3);



        int expected = 9;

        assertEquals(expected, result);
    }


}
