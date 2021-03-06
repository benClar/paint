package drawing.event;

/**
 * State event signalling application to quit
 */
public class QuitStateEvent extends StateEvent {

    private final EventType type = EventType.STATE_QUIT;

    @Override
    public EventType getType() {
        return type;
    }
}
