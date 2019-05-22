package drawing.eventhub;

import drawing.event.Event;
import drawing.eventhub.hubpublisher.EventHubPublisher;

public class EventHubImpl implements EventHub {

    private final EventHubPublisher eventPublisher;


    public EventHubImpl(EventHubPublisher eventPublisher){
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
