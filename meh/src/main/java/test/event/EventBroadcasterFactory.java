package test.event;

public interface EventBroadcasterFactory {

    EventBroadcaster createEventBroadcaster();

    EventBroadcaster.CompositeSubscription createCompositeSubscription();

}
