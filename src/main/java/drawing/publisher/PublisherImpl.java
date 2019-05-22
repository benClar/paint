package drawing.publisher;

import drawing.event.Event;
import drawing.eventhub.EventHub;

public class PublisherImpl<E extends Event> implements Publisher<E> {

    private final EventHub eventHub;
    private final Class<? extends Event> type;

    public PublisherImpl(EventHub eventHub, Class<? extends Event> type) {
        this.eventHub = eventHub;
        this.type = type;
    }

    @Override
    public void publish(E event) {
        eventHub.put(type, event);
    }
}
