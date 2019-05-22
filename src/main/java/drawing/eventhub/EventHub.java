package drawing.eventhub;

import drawing.event.Event;

/**
 * Hub for events fpr subscription and publication.  Layer before events get published out
 * to allow for management e.g. implementation of a queue of events
 */
public interface EventHub {
    void put(Class<? extends Event> eventType, Event e);
    void subscribe(Class<? extends Event> eventType, ListenerCallback listener);
}
