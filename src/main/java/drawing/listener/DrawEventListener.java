package drawing.listener;

import drawing.event.DrawEvent;
import drawing.event.Event;
import drawing.event.GraphicsEvent;
import drawing.event.LineDrawEvent;
import drawing.event.MessageGraphicsEvent;
import drawing.event.RectDrawEvent;
import drawing.event.StateDrawEvent;
import drawing.event.StateEvent;
import drawing.publisher.Publisher;
import drawing.util.Graphics;

/**
 * An {@link EventListener} that listens for {@link DrawEvent} and converts them to
 * {@link StateEvent} then publishes them.  Will also publishgin {@link GraphicsEvent}
 * for error messages.
 */
public class DrawEventListener implements EventListener {
    private final Publisher<StateEvent> statePublisher;
    private final Publisher<GraphicsEvent> graphicsPublisher;

    /**
     * Constructor for {@link DrawEventListener}
     * @param statePublisher for publishing state events
     * @param graphicsPublisher for publishing graphics events.
     */
    public DrawEventListener(Publisher<StateEvent> statePublisher, Publisher<GraphicsEvent> graphicsPublisher){
        this.statePublisher = statePublisher;
        this.graphicsPublisher = graphicsPublisher;
    }
    /**
     * Callback for when a {@link DrawEvent} is received.
     * @param event new event to be processed
     * @throws IllegalArgumentException occurs when unrecognised event type is received.
     */
    public void onDrawEvent(Event event) {
        if(event instanceof LineDrawEvent){
            LineDrawEvent lineDrawEvent = (LineDrawEvent) event;
            lineToStateDrawEvent(lineDrawEvent);
        } else if(event instanceof RectDrawEvent){
            RectDrawEvent rectDrawEvent = (RectDrawEvent) event;
            rectToStateDrawEvent(rectDrawEvent);
        } else {
            throw new IllegalArgumentException("Unknown event type " + event.getType());
        }
    }
    /**
     * Generates and published coordinates for a rectangle given a {@link RectDrawEvent}
     * @param rectDrawEvent event cotaining just start and end coordinates
     * @throws IllegalArgumentException occurs when unrecognised event type is received.
     */
    private void rectToStateDrawEvent(RectDrawEvent rectDrawEvent) {
        int currCol = rectDrawEvent.getColStart();
        int currRow = rectDrawEvent.getRowStart();

        StateDrawEvent.StateDrawEventBuilder builder = StateDrawEvent.builder();
        while(currCol < rectDrawEvent.getColEnd()){
            builder.addCoordinate(currRow, currCol++);
        }
        while(currRow < rectDrawEvent.getRowEnd()){
            builder.addCoordinate(currRow++, currCol);

        }
        while(currCol > rectDrawEvent.getColStart()){
            builder.addCoordinate(currRow, currCol--);

        }
        while(currRow > rectDrawEvent.getRowStart()){
            builder.addCoordinate(currRow--, currCol);

        }
        statePublisher.publish(builder.build());
    }

    /**
     * Generates coordinates for a horizontal line given a {@link LineDrawEvent}
     * @param event event contaiing just start and end coordinates
     * @return a StateDrawEvent holding a list of coordinates to draw
     */
    private StateDrawEvent horizontalLine(LineDrawEvent event){
        int currCol = Math.min(event.getColStart(), event.getColEnd());
        int endCol = Math.max(event.getColStart(), event.getColEnd());
        StateDrawEvent.StateDrawEventBuilder builder = StateDrawEvent.builder();
        while(currCol <= endCol){
            builder.addCoordinate(event.getRowStart(), currCol);
            currCol++;
        }
        return builder.build();
    }

    /**
     * Generates coordinates for a vertical line given a {@link LineDrawEvent}
     * @param event event contaiing just start and end coordinates
     * @return a StateDrawEvent holding a list of coordinates to draw
     */
    private StateEvent verticalLine(LineDrawEvent event){
        int currRow = Math.min(event.getRowStart(), event.getRowEnd());
        int endRow = Math.max(event.getRowStart(), event.getRowEnd());
        StateDrawEvent.StateDrawEventBuilder builder = StateDrawEvent.builder();
        while(currRow <= endRow){
            builder.addCoordinate(currRow, event.getColStart());
            currRow++;
        }
        return builder.build();
    }

    /**
     * publishes event with coordinates for a line given a {@link LineDrawEvent}
     * or publishes error message to graphics listener
     * @param event event contaiing just start and end coordinates
     */
    private void lineToStateDrawEvent(LineDrawEvent event) {
        if (event.getRowStart() == event.getRowEnd()) {
            statePublisher.publish(horizontalLine(event));
        } else if (event.getColStart() == event.getColEnd()) {
            statePublisher.publish(verticalLine(event));
        } else {
            graphicsPublisher.publish(MessageGraphicsEvent.builder()
                    .message(Graphics.NOT_A_LINE_ERR)
                    .build());
        }
    }
}
