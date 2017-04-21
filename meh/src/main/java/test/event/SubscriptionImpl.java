package test.event;

import io.reactivex.disposables.Disposable;

class SubscriptionImpl implements EventBroadcaster.Subscription {

    final Disposable disposable;

    SubscriptionImpl(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

}
