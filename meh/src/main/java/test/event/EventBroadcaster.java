package test.event;

public interface EventBroadcaster {

    void broadcast(Event event);

    <E extends Event> Subscription subscribe(Class<E> eventClass, EventHandler<E> handler);

    interface Subscription {

        void unsubscribe();

    }

    interface CompositeSubscription extends Subscription {

        void add(Subscription subscription);

        void remove(Subscription subscription);

    }

}
