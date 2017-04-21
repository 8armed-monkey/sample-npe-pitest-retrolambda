package test.event;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestEventBroadcasterFactoryImpl {

    private EventBroadcasterFactory factory;

    @Before
    public void setUp() {
        factory = new EventBroadcasterFactoryImpl();
    }

    @Test
    public void createCompositeSubscription_instanceOfCompositeSubscriptionImpl() {
        assertEquals(CompositeSubscriptionImpl.class, factory.createCompositeSubscription().getClass());
    }

    @Test
    public void createEventBroadcaster_instanceOfEventBroadcasterImpl() {
        assertEquals(EventBroadcasterImpl.class, factory.createEventBroadcaster().getClass());
    }

}
