package drawing.eventhub.hubpublisher;

import drawing.event.Event;
import drawing.event.MessageGraphicsEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EventHubPublisherImplTest {

    private List<Event> events;

    @Before
    public void setup(){
        events = new ArrayList<>();
    }

    @Test
    public void testSubscribe(){
        EventHubPublisher publisher = new EventHubPublisherImpl();
        publisher.subscribe(MessageGraphicsEvent.class, e->events.add(e));
    }

    @Test
    public void testValidPublish(){
        EventHubPublisher publisher = new EventHubPublisherImpl();
        publisher.subscribe(MessageGraphicsEvent.class, e->events.add(e));
        publisher.publish(MessageGraphicsEvent.class, MessageGraphicsEvent
                .builder()
                .message("test")
                .build());
        assertEquals(events.size(), 1);
    }

    @Test(expected=IllegalStateException.class)
    public void testinvalidPublish(){
        EventHubPublisher publisher = new EventHubPublisherImpl();
        publisher.publish(MessageGraphicsEvent.class, MessageGraphicsEvent
                .builder()
                .message("test")
                .build());
        assertEquals(events.size(), 1);
    }

}