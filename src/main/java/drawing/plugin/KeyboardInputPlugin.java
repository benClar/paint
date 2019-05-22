package drawing.plugin;

import drawing.publisher.Publisher;
import drawing.event.InputEvent;

import java.util.Scanner;

public class KeyboardInputPlugin implements Plugin {

    private final Publisher<InputEvent> publisher;

    public KeyboardInputPlugin(Publisher<InputEvent> publisher){

        this.publisher = publisher;
    }

    @Override
    public void exec() {
        Scanner myObj = new Scanner(System.in);
        String input = myObj.nextLine();
        publisher.publish(new InputEvent(input));
    }
}
