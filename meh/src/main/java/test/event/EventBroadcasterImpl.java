package test.event;

import io.reactivex.subjects.PublishSubject;

class EventBroadcasterImpl implements EventBroadcaster {

    private PublishSubject<Event> publisher;

    public EventBroadcasterImpl() {
        publisher = PublishSubject.create();
    }

    @Override
    public void broadcast(Event event) {
        publisher.onNext(event);
    }

    @Override
    public <E extends Event> Subscription subscribe(Class<E> eventClass, EventHandler<E> handler) {
        return new SubscriptionImpl(publisher
                .filter(evt -> eventClass.isAssignableFrom(evt.getClass()))
                .map(eventClass::cast)
                .subscribe(handler::handle));
    }

}
