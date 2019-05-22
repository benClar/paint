package drawing.listener;

import drawing.canvas.CanvasCoordinate;
import drawing.event.DrawEvent;
import drawing.event.Event;
import drawing.event.GraphicsEvent;
import drawing.event.LineDrawEvent;
import drawing.event.RectDrawEvent;
import drawing.event.StateDrawEvent;
import drawing.event.StateEvent;
import drawing.publisher.Publisher;
import drawing.eventhub.EventHub;
import drawing.eventhub.EventHubImpl;
import drawing.eventhub.hubpublisher.EventHubPublisherImpl;
import drawing.publisher.PublisherImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class DrawEventListenerTest {

    private DrawEventListener drawEventListener;
    private EventHub eventHub;
    private ArrayList<Event> events;

    @Before
    public void setup(){
        eventHub = new EventHubImpl(new EventHubPublisherImpl());
        Publisher<StateEvent> stateEventPub = new PublisherImpl<>(eventHub, StateEvent.class);
        Publisher<GraphicsEvent> graphicsEventPublisher = new PublisherImpl<>(eventHub, GraphicsEvent.class);
        drawEventListener = new DrawEventListener(stateEventPub, graphicsEventPublisher);
        events = new ArrayList<>();
        eventHub.subscribe(StateEvent.class, e->events.add(e));
        eventHub.subscribe(GraphicsEvent.class, e->events.add(e));
    }

    @Test
    public void testDrawVerticalLine(){
        DrawEvent event = LineDrawEvent.builder()
                .rowStart(3)
                .colStart(4)
                .rowEnd(8)
                .colEnd(4)
                .build();
        drawEventListener.onDrawEvent(event);
        int[] expected = {3, 4, 5, 6, 7, 8};
        StateDrawEvent result = (StateDrawEvent) events.get(0);
        int i = 0;
        for(CanvasCoordinate coord : result) {
            assertEquals(coord.getRow(), expected[i++]);
            assertEquals(coord.getCol(), 4);
        }
    }

    @Test
    public void testDrawHorizontalLine(){
        DrawEvent event = LineDrawEvent.builder()
                .rowStart(4)
                .colStart(3)
                .rowEnd(4)
                .colEnd(8)
                .build();
        drawEventListener.onDrawEvent(event);
        int[] expected = {3, 4, 5, 6, 7, 8};
        StateDrawEvent result = (StateDrawEvent) events.get(0);
        int i = 0;
        for(CanvasCoordinate coord : result) {
            assertEquals(coord.getCol(), expected[i++]);
            assertEquals(coord.getRow(), 4);
        }
    }

    @Test
    public void testDrawRect(){
        DrawEvent event = RectDrawEvent.builder()
                .rowStart(1)
                .colStart(2)
                .rowEnd(3)
                .colEnd(4)
                .build();
        drawEventListener.onDrawEvent(event);
        int[] expectedRow = {1, 1, 1, 2, 3, 3, 3, 2};
        int[] expectedCol = {2, 3, 4, 4, 4, 3, 2, 2};

        StateDrawEvent result = (StateDrawEvent) events.get(0);

        int i = 0;
        for(CanvasCoordinate coord : result) {
            assertEquals(coord.getRow(), expectedRow[i]);
            assertEquals(coord.getCol(), expectedCol[i]);
            i++;
        }
    }

}
