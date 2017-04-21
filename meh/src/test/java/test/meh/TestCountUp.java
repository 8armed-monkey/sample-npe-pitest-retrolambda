package test.meh;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCountUp {

    private CountUp countUp;

    @Before
    public void setUp() {
        countUp = new CountUp(0, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_currentGreaterThanExpected_throwIllegalArgumentException() {
        countUp = new CountUp(1, 0);
    }

    @Test
    public void increment_currentLessThanExpected_currentIncremented() {
        assertEquals(1, countUp.increment());
        assertEquals(1, countUp.getCurrent());
    }

    @Test
    public void increment_currentEqualsToExpected_currentRemains() {
        countUp = new CountUp(3, 3);
        assertEquals(3, countUp.increment());
        assertEquals(3, countUp.getCurrent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void incrementBy_negativeValue_throwIllegalArgumentException() {
        countUp.incrementBy(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void incrementBy_zeroValue_throwIllegalArgumentException() {
        countUp.incrementBy(0);
    }

    @Test
    public void incrementBy_positiveValue_currentIncremented() {
        assertEquals(2, countUp.incrementBy(2));
    }

    @Test
    public void incrementBy_veryLargePositiveValue_currentIncrementedAndNotExceedExpected() {
        assertEquals(3, countUp.incrementBy(100));
    }

    @Test
    public void getExpected_expectedReturned() {
        assertEquals(3, countUp.getExpected());
    }

    @Test
    public void hasReachedExpected_currentLessThanExpected_returnFalse() {
        countUp = new CountUp(0, 1);
        assertFalse(countUp.hasReachedExpected());
    }

    @Test
    public void hasReachedExpected_currentEqualToExpected_returnTrue() {
        countUp = new CountUp(1, 1);
        assertTrue(countUp.hasReachedExpected());
    }

}
