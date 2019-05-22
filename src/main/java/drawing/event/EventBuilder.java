package drawing.event;

/**
 * Event builder base
 */
public interface EventBuilder<E extends Event> {

    E build();
}
