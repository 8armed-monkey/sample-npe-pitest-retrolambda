package test.event;

public class EventBroadcasterFactoryImpl implements EventBroadcasterFactory {

    @Override
    public EventBroadcaster createEventBroadcaster() {
        return new EventBroadcasterImpl();
    }

    @Override
    public EventBroadcaster.CompositeSubscription createCompositeSubscription() {
        return new CompositeSubscriptionImpl();
    }

}
