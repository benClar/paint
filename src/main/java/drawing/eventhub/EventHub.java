package drawing.eventhub;

import drawing.event.Event;

public interface EventHub {
    void put(Class<? extends Event> eventType, Event e);
    void subscribe(Class<? extends Event> eventType, ListenerCallback listener);
}
