package drawing.eventhub;

import drawing.event.Event;
import drawing.eventhub.hubpublisher.EventHubPublisher;

/**
 * Allows subscription for {@link Event}s and distributes {@link Event}s
 * to subscribers.  Simple hub that passes events straight for publication as
 * soon as received.
 */
public class SimpleEventHubImpl implements EventHub {

    private final EventHubPublisher eventPublisher;


    public SimpleEventHubImpl(EventHubPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void put(Class<? extends Event> eventType, Event event) {
        eventPublisher.publish(eventType, event);
    }

    @Override
    public void subscribe(Class<? extends Event> eventType, ListenerCallback listener) {
        eventPublisher.subscribe(eventType, listener);
    }
}
