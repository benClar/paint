package drawing.eventhub;

import drawing.event.CreateStateEvent;
import drawing.event.Event;
import drawing.event.InputEvent;
import drawing.event.StateEvent;
import drawing.eventhub.hubpublisher.EventHubPublisherImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EventHubTest {

    @Test
    public void testInputEventPublish(){
        EventHub eventHub = new EventHubImpl(new EventHubPublisherImpl());
        List<Event> events = new ArrayList<>();
        eventHub.subscribe(InputEvent.class, events::add);
        eventHub.put(InputEvent.class, new InputEvent("C 10 20"));
        assertEquals(events.size(),  1);
    }

    @Test
    public void testMultiSubscribers(){
        EventHub eventHub = new EventHubImpl(new EventHubPublisherImpl());
        List<String> events = new ArrayList<>();
        eventHub.subscribe(InputEvent.class,x-> events.add("listener_1"));
        eventHub.subscribe(StateEvent.class, x-> events.add("listener_2"));

        eventHub.put(InputEvent.class, new InputEvent("C 10 20"));
        eventHub.put(StateEvent.class, CreateStateEvent.builder().height(10).width(20).build());
        eventHub.put(InputEvent.class, new InputEvent("C 10 20"));
        assertEquals(events.get(0),"listener_1");
        assertEquals(events.get(1),"listener_2");
        assertEquals(events.get(2),"listener_1");
        assertEquals(events.size(), 3);
    }
}
