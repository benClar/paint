package drawing.eventhub;

import drawing.event.Event;

/**
 * Callback for when event is published
 */
public interface ListenerCallback {

    void call(Event event);
}
