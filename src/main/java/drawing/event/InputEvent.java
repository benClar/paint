package drawing.event;

public class InputEvent implements Event {

    private final String input;

    public InputEvent(String input){

        this.input = input;
    }

    public String getInput() {
        return input;
    }

    @Override
    public EventType getType() {
        return EventType.INPUT;
    }
}
