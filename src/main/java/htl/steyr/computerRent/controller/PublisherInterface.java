package htl.steyr.computerRent.controller;

public interface PublisherInterface<T> {

    public void addSubscriber(SubscriberInterface<Boolean> sub);

    public void removeSubscriber(SubscriberInterface<T> sub);

    public void notifyAllSubscriber(T what);

}
