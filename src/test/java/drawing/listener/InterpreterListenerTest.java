package drawing.listener;

import drawing.event.CreateStateEvent;
import drawing.event.DrawEvent;
import drawing.event.Event;
import drawing.event.EventType;
import drawing.event.GraphicsEvent;
import drawing.event.InputEvent;
import drawing.event.LineDrawEvent;
import drawing.event.MessageGraphicsEvent;
import drawing.event.RectDrawEvent;
import drawing.event.StateEvent;
import drawing.publisher.Publisher;
import drawing.eventhub.EventHub;
import drawing.eventhub.EventHubImpl;
import drawing.eventhub.hubpublisher.EventHubPublisherImpl;
import drawing.publisher.PublisherImpl;
import drawing.util.Graphics;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class InterpreterListenerTest {

    private InterpreterListener interpreterListener;
    private EventHub eventHub;
    private ArrayList<Event> events;

    @Before
    public void setup(){
        eventHub = new EventHubImpl(new EventHubPublisherImpl());
        Publisher<StateEvent> stateEventPub = new PublisherImpl<>(eventHub, StateEvent.class);
        Publisher<GraphicsEvent> graphicsEventPublisher = new PublisherImpl<>(eventHub, GraphicsEvent.class);
        Publisher<DrawEvent> drawEventPublisher = new PublisherImpl<>(eventHub, GraphicsEvent.class);
        interpreterListener = new InterpreterListener(stateEventPub, graphicsEventPublisher, drawEventPublisher);
        events = new ArrayList<>();
        eventHub.subscribe(StateEvent.class, e->events.add(e));
        eventHub.subscribe(GraphicsEvent.class, e->events.add(e));
    }

    @Test
    public void testEmpty(){
        InputEvent event = new InputEvent("");
        interpreterListener.interpretAndPublish(event);
        assertEquals(events.size(), 0);
    }

    @Test
    public void testSpaces(){
        InputEvent event = new InputEvent("     ");
        interpreterListener.interpretAndPublish(event);
        assertEquals(events.size(), 0);
    }

    @Test
    public void testValidCreateEvent(){
        InputEvent event = new InputEvent("C    10    20");
        interpreterListener.interpretAndPublish(event);
        Event result = events.get(0);
        assertEquals(EventType.CREATE, result.getType());
        CreateStateEvent stateEvent = (CreateStateEvent) result;
        assertEquals(10, stateEvent.getWidth());
        assertEquals(20, stateEvent.getHeight());
    }

    @Test
    public void testInValidCreateEvent(){
        InputEvent event = new InputEvent("C       20");
        interpreterListener.interpretAndPublish(event);
        Event result = events.get(0);
        assertEquals(EventType.MESSAGE, result.getType());
        MessageGraphicsEvent messageEvent = (MessageGraphicsEvent) result;
        assertEquals(messageEvent.getMessage(), Graphics.CREATE_ERR);
    }

    @Test
    public void testValidRectDrawEvent(){
        InputEvent event = new InputEvent("R   15 2 20 5");
        interpreterListener.interpretAndPublish(event);
        Event result = events.get(0);
        assertEquals(EventType.RECT_DRAW, result.getType());
        RectDrawEvent stateEvent = (RectDrawEvent) result;
        assertEquals(1, stateEvent.getRowStart());
        assertEquals(14, stateEvent.getColStart());
        assertEquals(4, stateEvent.getRowEnd());
        assertEquals(19, stateEvent.getColEnd());
    }

    @Test
    public void testValidLineDrawEvent(){
        InputEvent event = new InputEvent("L   1 3 7 3");
        interpreterListener.interpretAndPublish(event);
        Event result = events.get(0);
        assertEquals(EventType.LINE_DRAW, result.getType());
        LineDrawEvent stateEvent = (LineDrawEvent) result;
        assertEquals(2, stateEvent.getRowStart());
        assertEquals(0, stateEvent.getColStart());
        assertEquals(2, stateEvent.getRowEnd());
        assertEquals(6, stateEvent.getColEnd());
    }

    @Test
    public void testQuitStateEvent(){
        InputEvent event = new InputEvent("Q");
        interpreterListener.interpretAndPublish(event);
        Event result = events.get(0);
        assertEquals(EventType.STATE_QUIT, result.getType());
    }

    @Test
    public void testIncompleteLineDrawEvent(){
        InputEvent event = new InputEvent("L   1 3 ");
        interpreterListener.interpretAndPublish(event);
        Event result = events.get(0);
        assertEquals(EventType.MESSAGE, result.getType());
        assertEquals(events.size(), 1);
    }

    @Test
    public void testIncompleteRectDrawEvent(){
        InputEvent event = new InputEvent("R  1 3 ");
        interpreterListener.interpretAndPublish(event);
        Event result = events.get(0);
        assertEquals(EventType.MESSAGE, result.getType());
        assertEquals(events.size(), 1);
    }

}
