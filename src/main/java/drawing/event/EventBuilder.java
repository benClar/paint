package drawing.event;

public interface EventBuilder<E extends Event> {

    public E build();
}
