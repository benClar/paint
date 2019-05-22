package drawing.eventhub.hubpublisher;

import drawing.event.Event;
import drawing.eventhub.ListenerCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps events to subscriptions for publication.
 */
public class EventHubPublisherImpl implements EventHubPublisher {

    private final Map<Class, List<ListenerCallback>> listeners = new HashMap<>();

    @Override
    public void subscribe(Class<? extends Event> eventType, ListenerCallback listener) {
        List<ListenerCallback> listenersForEvent = listeners.get(eventType);
        if(listenersForEvent == null) {
            listenersForEvent = new ArrayList<>();
            listeners.put(eventType, listenersForEvent);
        }
        listenersForEvent.add(listener);
    }

    @Override
    public void publish(Class type, Event event) {
        List<ListenerCallback> listenersForEvent = listeners.get(type);
        if(listenersForEvent != null) {
            try {
                listenersForEvent.forEach(l -> l.call(event));
            } catch( Exception e){
                System.out.println("error whilst processing: " +  e.getMessage());
            }
        } else {
            throw new IllegalStateException("No subscribers for " + type);
        }
    }
}
