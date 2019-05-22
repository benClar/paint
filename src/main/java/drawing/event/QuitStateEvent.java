package drawing.event;

public class QuitStateEvent extends StateEvent {

    private final EventType type = EventType.STATE_QUIT;

    @Override
    public EventType getType() {
        return type;
    }
}
