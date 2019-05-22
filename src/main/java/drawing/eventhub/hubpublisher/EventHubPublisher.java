package drawing.eventhub.hubpublisher;

import drawing.event.Event;
import drawing.eventhub.ListenerCallback;

/**
 * Publishes events from event hub.
 */
public interface EventHubPublisher {
    void subscribe(Class<? extends Event> eventType, ListenerCallback listener);
    void publish(Class type, Event e);
}
