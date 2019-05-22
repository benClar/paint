package drawing.eventhub;

import drawing.event.Event;

public interface ListenerCallback {

    public void call(Event event);
}
