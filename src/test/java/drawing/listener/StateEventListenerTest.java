package drawing.listener;

import drawing.event.*;
import drawing.publisher.Publisher;
import drawing.eventhub.EventHub;
import drawing.eventhub.SimpleEventHubImpl;
import drawing.eventhub.hubpublisher.EventHubPublisherImpl;
import drawing.publisher.PublisherImpl;
import drawing.util.Graphics;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class StateEventListenerTest {
    private StateEventListener stateEventListener;
    private EventHub eventHub;
    private ArrayList<Event> events;

    @Before
    public void setup(){
        eventHub = new SimpleEventHubImpl(new EventHubPublisherImpl());
        Publisher<GraphicsEvent> graphicsEventPublisher = new PublisherImpl<>(eventHub, GraphicsEvent.class);
        stateEventListener = new StateEventListener(graphicsEventPublisher);
        events = new ArrayList<>();
        eventHub.subscribe(GraphicsEvent.class, e->events.add(e));
        stateEventListener.onStateEvent(CreateStateEvent.builder()
                .width(20)
                .height(5)
                .build());
    }

    @Test
    public void testOutOfBoundsDrawX(){
        stateEventListener.onStateEvent(StateDrawEvent.builder()
                .addCoordinate(3, 16)
                .addCoordinate(3, 17)
                .addCoordinate(3, 18)
                .addCoordinate(3, 19)
                .addCoordinate(3, 20)
        .build());
        Event result = events.get(1);
        assertEquals(EventType.MESSAGE, result.getType());
        MessageGraphicsEvent messageEvent = (MessageGraphicsEvent) result;
        assertEquals(messageEvent.getMessage(), Graphics.coordErrorMessage(3, 20, 20, 5));
        result = events.get(2);
        CanvasGraphicsEvent canvasEvent = (CanvasGraphicsEvent) result;

        for(int row = 0; row < canvasEvent.getCanvas().getHeight(); row++){
            for(int column = 0; column < canvasEvent.getCanvas().getWidth(); column++){
                assertEquals(canvasEvent.getCanvas().get(row, column), 0);
            }
        }

    }

    @Test
    public void testOutOfBoundsDrawY(){
        stateEventListener.onStateEvent(StateDrawEvent.builder()
                .addCoordinate(2, 16)
                .addCoordinate(3, 16)
                .addCoordinate(4, 16)
                .addCoordinate(5, 16)
                .build());
        Event result = events.get(1);
        assertEquals(EventType.MESSAGE, result.getType());
        MessageGraphicsEvent messageEvent = (MessageGraphicsEvent) result;
        assertEquals(messageEvent.getMessage(), Graphics.coordErrorMessage(5, 16, 20, 5));

        result = events.get(2);
        CanvasGraphicsEvent canvasEvent = (CanvasGraphicsEvent) result;

        for(int row = 0; row < canvasEvent.getCanvas().getHeight(); row++){
            for(int column = 0; column < canvasEvent.getCanvas().getWidth(); column++){
                assertEquals(canvasEvent.getCanvas().get(row, column), 0);
            }
        }
    }

    @Test
    public void testStateQuit(){
        assertEquals(stateEventListener.shouldContinue(), true);
        stateEventListener.onStateEvent(new QuitStateEvent());
        assertEquals(stateEventListener.shouldContinue(), false);
    }
}
