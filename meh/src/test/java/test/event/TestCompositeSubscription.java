package test.event;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TestCompositeSubscription {

    private EventBroadcaster eventBroadcaster;
    private EventBroadcaster.CompositeSubscription compositeSubscription;

    @Before
    public void setUp() {
        eventBroadcaster = new EventBroadcasterImpl();
        compositeSubscription = new CompositeSubscriptionImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_incompatibleSubscription_throwIllegalArgumentException() {
        compositeSubscription.add(mock(EventBroadcaster.Subscription.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void remove_incompatibleSubscription_throwIllegalArgumentException() {
        compositeSubscription.remove(mock(EventBroadcaster.Subscription.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void remove_beforeCompositeSubscriptionDispose_notDisposed() {
        EventHandler<SampleEvent1> eventHandler1 = (EventHandler<SampleEvent1>) mock(EventHandler.class);
        EventHandler<SampleEvent2> eventHandler2 = (EventHandler<SampleEvent2>) mock(EventHandler.class);

        EventBroadcaster.Subscription s1 = eventBroadcaster.subscribe(SampleEvent1.class, eventHandler1);
        EventBroadcaster.Subscription s2 = eventBroadcaster.subscribe(SampleEvent2.class, eventHandler2);

        compositeSubscription.add(s1);
        compositeSubscription.add(s2);

        eventBroadcaster.broadcast(new SampleEvent1());
        eventBroadcaster.broadcast(new SampleEvent2());
        eventBroadcaster.broadcast(new SampleEvent2());

        compositeSubscription.remove(s1);
        compositeSubscription.unsubscribe();

        eventBroadcaster.broadcast(new SampleEvent1());
        eventBroadcaster.broadcast(new SampleEvent1());
        eventBroadcaster.broadcast(new SampleEvent2());

        verify(eventHandler1, times(3)).handle(any(SampleEvent1.class));
        verify(eventHandler2, times(2)).handle(any(SampleEvent2.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void unsubscribe_allSubscriptionsAreDisposed() {
        EventHandler<SampleEvent1> eventHandler1 = (EventHandler<SampleEvent1>) mock(EventHandler.class);
        EventHandler<SampleEvent2> eventHandler2 = (EventHandler<SampleEvent2>) mock(EventHandler.class);
        EventHandler<SampleEvent3> eventHandler3 = (EventHandler<SampleEvent3>) mock(EventHandler.class);

        compositeSubscription.add(eventBroadcaster.subscribe(SampleEvent1.class, eventHandler1));
        compositeSubscription.add(eventBroadcaster.subscribe(SampleEvent2.class, eventHandler2));
        compositeSubscription.add(eventBroadcaster.subscribe(SampleEvent3.class, eventHandler3));

        eventBroadcaster.broadcast(new SampleEvent1());
        eventBroadcaster.broadcast(new SampleEvent2());
        eventBroadcaster.broadcast(new SampleEvent2());

        compositeSubscription.unsubscribe();

        eventBroadcaster.broadcast(new SampleEvent1());
        eventBroadcaster.broadcast(new SampleEvent3());

        verify(eventHandler1, times(1)).handle(any(SampleEvent1.class));
        verify(eventHandler2, times(2)).handle(any(SampleEvent2.class));
        verify(eventHandler3, never()).handle(any(SampleEvent3.class));
    }

}
