package drawing.event;

/**
 * Draw event holding start and end poitns of coordinates for a rectangle
 */
public class RectDrawEvent extends DrawEvent {
    private final EventType type = EventType.RECT_DRAW;


    private RectDrawEvent(int rowStart, int colStart, int rowEnd, int colEnd) {
        super(rowStart, colStart, rowEnd, colEnd);
    }

    public static class RectDrawEventBuilder extends DrawEvent.DrawEventBuilder {

        @Override
        public DrawEvent build() {
            return new RectDrawEvent(rowStart, colStart, rowEnd, colEnd);
        }
    }

    public static DrawEvent.DrawEventBuilder builder(){
        return new RectDrawEventBuilder();
    }

    @Override
    public EventType getType() {
        return type;
    }
}
