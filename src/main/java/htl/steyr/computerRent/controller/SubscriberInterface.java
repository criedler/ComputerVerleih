package htl.steyr.computerRent.controller;

public interface SubscriberInterface<T> {

    void notify(T what);

}
