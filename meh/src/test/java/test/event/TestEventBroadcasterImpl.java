package test.event;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class TestEventBroadcasterImpl {

    private EventBroadcaster eventBroadcaster;

    @Before
    public void setUp() {
        eventBroadcaster = new EventBroadcasterImpl();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void broadcast_subscribedEvent_receivedBySubscribers() {
        EventHandler<SampleEvent1> eventHandler = (EventHandler<SampleEvent1>) mock(EventHandler.class);
        eventBroadcaster.subscribe(SampleEvent1.class, eventHandler);

        SampleEvent1 event = new SampleEvent1();
        eventBroadcaster.broadcast(event);

        verify(eventHandler).handle(eq(event));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void broadcast_nonSubscribedEvent_notReceivedBySubscribers() {
        EventHandler<SampleEvent1> eventHandler = (EventHandler<SampleEvent1>) mock(EventHandler.class);
        eventBroadcaster.subscribe(SampleEvent1.class, eventHandler);

        SampleEvent2 event = new SampleEvent2();
        eventBroadcaster.broadcast(event);

        verify(eventHandler, never()).handle(any());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void broadcast_subscribedParentEvent_receivedBySubscribers() {
        EventHandler<Event> eventHandler = (EventHandler<Event>) mock(EventHandler.class);
        eventBroadcaster.subscribe(Event.class, eventHandler);

        eventBroadcaster.broadcast(new SampleEvent1());
        eventBroadcaster.broadcast(new SampleEvent2());

        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(eventHandler, times(2)).handle(captor.capture());

        assertEquals(SampleEvent1.class, captor.getAllValues().get(0).getClass());
        assertEquals(SampleEvent2.class, captor.getAllValues().get(1).getClass());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void unsubscribe_event_notReceivedBySubscribers() {
        EventHandler<Event> eventHandler = (EventHandler<Event>) mock(EventHandler.class);
        EventBroadcaster.Subscription subscription =
                eventBroadcaster.subscribe(Event.class, eventHandler);

        eventBroadcaster.broadcast(new SampleEvent1());
        eventBroadcaster.broadcast(new SampleEvent2());

        subscription.unsubscribe();

        eventBroadcaster.broadcast(new SampleEvent3());

        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(eventHandler, times(2)).handle(captor.capture());

        assertEquals(SampleEvent1.class, captor.getAllValues().get(0).getClass());
        assertEquals(SampleEvent2.class, captor.getAllValues().get(1).getClass());
    }

}
