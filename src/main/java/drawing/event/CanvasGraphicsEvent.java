package drawing.event;

import drawing.canvas.Canvas;


/**
 * Holds canvas to be output to screen
 */
public class CanvasGraphicsEvent extends GraphicsEvent {

    private final EventType type = EventType.CANVAS_GRAPHICS;

    private final Canvas canvas;

    public CanvasGraphicsEvent(Canvas canvas){
        this.canvas = canvas;
    }

    public EventType getType(){
        return type;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
