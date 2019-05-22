package drawing.event;

/**
 * Event representing start and end coordinates for a line to be drawn
 */
public class LineDrawEvent extends DrawEvent {

    private final EventType type = EventType.LINE_DRAW;


    private LineDrawEvent(int rowStart, int colStart, int rowEnd, int colEnd) {
        super(rowStart, colStart, rowEnd, colEnd);
    }

    public static class LineDrawEventBuilder extends DrawEvent.DrawEventBuilder {

        @Override
        public DrawEvent build() {
            return new LineDrawEvent(rowStart, colStart, rowEnd, colEnd);
        }
    }

    public static DrawEvent.DrawEventBuilder builder(){
        return new LineDrawEventBuilder();
    }

    @Override
    public EventType getType() {
        return type;
    }
}
