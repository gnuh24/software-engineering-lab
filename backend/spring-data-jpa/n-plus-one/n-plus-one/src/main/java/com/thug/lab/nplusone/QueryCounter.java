package com.thug.lab.nplusone;

public class QueryCounter {
    public static int count = 0;

    public static void increment() {
        count++;
    }

    public static void reset() {
        count = 0;
    }
}
