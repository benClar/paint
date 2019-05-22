package drawing.event;

import drawing.canvas.CanvasCoordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StateDrawEvent extends StateEvent implements Iterable<CanvasCoordinate>{
    private final List<CanvasCoordinate> coordinates;

    private final EventType type = EventType.STATE_DRAW;

    private StateDrawEvent(List<CanvasCoordinate> coordinates){
        this.coordinates = coordinates;
    }

    @Override
    public Iterator<CanvasCoordinate> iterator() {
        return coordinates.iterator();
    }


    public static class StateDrawEventBuilder {
        private final List<CanvasCoordinate> coordinates = new ArrayList<>();

        private StateDrawEventBuilder(){}

        public StateDrawEventBuilder addCoordinate(int row, int col){
            coordinates.add(new CanvasCoordinate(row, col));
            return this;
        }

        public StateDrawEvent build() {
            return new StateDrawEvent(coordinates);
        }
    }

    public static StateDrawEventBuilder builder(){
        return new StateDrawEventBuilder();
    }

    public EventType getType(){
        return type;
    }
}
