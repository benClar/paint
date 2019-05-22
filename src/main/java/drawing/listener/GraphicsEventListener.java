package drawing.listener;

import drawing.event.CanvasGraphicsEvent;
import drawing.event.Event;
import drawing.event.EventType;
import drawing.event.MessageGraphicsEvent;
import drawing.util.Graphics;

public class GraphicsEventListener {

    public void onGraphicsEvent(Event event){
        if(EventType.MESSAGE.equals(event.getType())) {
            MessageGraphicsEvent msgGraphicsEvent = (MessageGraphicsEvent) event;
            System.out.printf(msgGraphicsEvent.getMessage());
        } else if(EventType.CANVAS_GRAPHICS.equals(event.getType())){
            CanvasGraphicsEvent canvasGraphicsEvent = (CanvasGraphicsEvent) event;
            printCanvas(canvasGraphicsEvent);

        } else {
            throw new IllegalArgumentException("Unknown graphics event type " + event.getType());
        }
    }

    private void printHorizontalFrame(int cols){
        for(int i = 0; i < cols + 2; i++){
            System.out.printf(Graphics.TOP_FRAME);
        }
        System.out.println();

    }

    private void printCanvas(CanvasGraphicsEvent canvasGraphicsEvent) {
        printHorizontalFrame(canvasGraphicsEvent.getCanvas().getWidth());
        for(int row = 0; row < canvasGraphicsEvent.getCanvas().getHeight(); row ++){
            System.out.printf(Graphics.SIDE_FRAME);
            for(int col = 0; col < canvasGraphicsEvent.getCanvas().getWidth(); col ++) {
                System.out.printf("%s", Graphics.paint[ canvasGraphicsEvent.getCanvas().get(row, col)]);
            }
            System.out.printf(Graphics.SIDE_FRAME);
            System.out.println();
        }
        printHorizontalFrame(canvasGraphicsEvent.getCanvas().getWidth());
        System.out.println();

    }

}
