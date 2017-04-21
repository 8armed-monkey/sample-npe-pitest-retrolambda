package test.meh;

import java.io.Serializable;

public class CountUp implements Serializable {

    private final int expected;
    private int current;

    public CountUp(int current, int expected) {
        if (current <= expected) {
            this.current = current;
            this.expected = expected;
        } else {
            throw new IllegalArgumentException("Current should be lte expected.");
        }
    }

    /**
     * Increment the count by 1 and return the new count.
     */
    public synchronized int increment() {
        return incrementBy(1);
    }

    /**
     * Increment the count by delta and return the new count. Delta has to be positive value.
     */
    public synchronized int incrementBy(int delta) {
        if (delta > 0) {
            return current = Math.min(expected, current + delta);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public synchronized int getCurrent() {
        return current;
    }

    public synchronized int getExpected() {
        return expected;
    }

    public synchronized boolean hasReachedExpected() {
        return current == expected;
    }

}
