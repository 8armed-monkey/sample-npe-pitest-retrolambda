package test.event;

import io.reactivex.disposables.CompositeDisposable;

class CompositeSubscriptionImpl implements EventBroadcaster.CompositeSubscription {

    private CompositeDisposable compositeDisposable;

    CompositeSubscriptionImpl() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }

    @Override
    public void add(EventBroadcaster.Subscription subscription) {
        if (subscription instanceof SubscriptionImpl) {
            compositeDisposable.add(((SubscriptionImpl) subscription).disposable);
        } else {
            throw new IllegalArgumentException(String.format(
                    "Incompatible subscription. Expected: %s", SubscriptionImpl.class.getName()));
        }
    }

    @Override
    public void remove(EventBroadcaster.Subscription subscription) {
        if (subscription instanceof SubscriptionImpl) {
            compositeDisposable.delete(((SubscriptionImpl) subscription).disposable);
        } else {
            throw new IllegalArgumentException(String.format(
                    "Incompatible subscription. Expected: %s", SubscriptionImpl.class.getName()));
        }
    }

}
