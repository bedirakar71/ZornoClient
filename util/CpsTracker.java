package com.senin.client.util;

import java.util.ArrayDeque;
import java.util.Deque;

public class CpsTracker {
    private static final Deque<Long> left = new ArrayDeque<>();
    private static final Deque<Long> right = new ArrayDeque<>();

    public static void leftClick() { left.addLast(System.currentTimeMillis()); }
    public static void rightClick() { right.addLast(System.currentTimeMillis()); }
    public static int getLeftCps() { return count(left); }
    public static int getRightCps() { return count(right); }

    private static int count(Deque<Long> q) {
        long now = System.currentTimeMillis();
        while (!q.isEmpty() && now - q.peekFirst() > 1000) q.pollFirst();
        return q.size();
    }
}
