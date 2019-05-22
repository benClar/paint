package drawing.listener;

import drawing.canvas.CanvasCoordinate;
import drawing.canvas.ImmutableCanvas;
import drawing.canvas.MutableCanvas;
import drawing.event.*;
import drawing.publisher.Publisher;
import drawing.util.Graphics;

/**
 * An {@link EventListener} that listens for {@link StateEvent}s and
 * converts them to other internal events for further processing or takes some action.
 */
public class StateEventListener {

    private MutableCanvas canvas;
    private final Publisher<GraphicsEvent> graphicsEventPublisher;
    private boolean shouldContinue = true;

    public StateEventListener(Publisher<GraphicsEvent> graphicsEventPublisher){
        this.graphicsEventPublisher = graphicsEventPublisher;
    }

    public void onStateEvent(Event event) {
        if(EventType.CREATE.equals(event.getType())) {
            CreateStateEvent createEvent = (CreateStateEvent) event;
            canvas = new MutableCanvas(createEvent.getWidth(), createEvent.getHeight());
            graphicsEventPublisher.publish(new CanvasGraphicsEvent(new ImmutableCanvas(canvas)));
        }else if(EventType.STATE_DRAW.equals(event.getType())){
            StateDrawEvent drawEvent = (StateDrawEvent) event;
            updateState(drawEvent);
            graphicsEventPublisher.publish(new CanvasGraphicsEvent(new ImmutableCanvas(canvas)));

        } else if(EventType.STATE_QUIT.equals(event.getType())){
            shouldContinue = false;
        }else {
            throw  new IllegalStateException("Unknown event type");
        }
    }

    private boolean isValidCoordinate(int row, int col){
        if(row < 0 || row >= canvas.getHeight()){
            return false;
        }
        if(col < 0 || col >= canvas.getWidth()){
            return false;
        }
        return true;
    }

    private void updateState(StateDrawEvent drawEvent) {
        MutableCanvas newCanvas = new MutableCanvas(canvas);
        for(CanvasCoordinate coord: drawEvent) {
            if(isValidCoordinate(coord.getRow(), coord.getCol())) {
                newCanvas.set(coord.getRow(), coord.getCol(), 1);
            } else {
                String msg = Graphics.coordErrorMessage(coord.getRow(),
                        coord.getCol(),
                        canvas.getWidth(),
                        canvas.getHeight());
                graphicsEventPublisher.publish(MessageGraphicsEvent.builder()
                        .message(msg)
                        .build());
                return;
            }
        }
        canvas = newCanvas;
    }
    
    public boolean shouldContinue(){
        return shouldContinue;
    }
}
