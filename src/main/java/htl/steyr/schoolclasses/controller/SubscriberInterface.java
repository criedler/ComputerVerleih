package htl.steyr.schoolclasses.controller;

public interface SubscriberInterface<T> {

    void notify(T what)    ;

}
