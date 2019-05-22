package drawing.publisher;

import drawing.event.CreateStateEvent;
import drawing.event.InputEvent;
import drawing.event.StateEvent;
import drawing.eventhub.EventHub;
import drawing.eventhub.SimpleEventHubImpl;
import drawing.eventhub.hubpublisher.EventHubPublisherImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PublisherImplTest {

    @Test
    public void testCreatePublisher(){
        new SimpleEventHubImpl(new EventHubPublisherImpl());
    }

    @Test
    public void testMultiSub(){
        EventHub eventHub = new SimpleEventHubImpl(new EventHubPublisherImpl());
        Publisher<StateEvent> stateEventpublisher = new PublisherImpl<>(eventHub, StateEvent.class);
        Publisher<InputEvent> inputEventpublisher = new PublisherImpl<>(eventHub, InputEvent.class);

        List<String> events = new ArrayList<>();
        eventHub.subscribe(InputEvent.class, x-> events.add("listener_1"));
        eventHub.subscribe(StateEvent.class, x-> events.add("listener_2"));
        stateEventpublisher.publish(CreateStateEvent.builder().height(10).width(20).build());
        inputEventpublisher.publish(new InputEvent("C 10 20"));
        stateEventpublisher.publish(CreateStateEvent.builder().height(10).width(20).build());
        assertEquals(events.get(0),"listener_2");
        assertEquals(events.get(1),"listener_1");
        assertEquals(events.get(2),"listener_2");
    }

}
