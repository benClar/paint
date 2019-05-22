package drawing.listener;

import drawing.event.*;
import drawing.publisher.Publisher;
import drawing.util.Graphics;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An {@link EventListener} that listens for {@link InputEvent} and
 * converts them to internal events for further processing.
 */
public class InputListener {

    private static final int CMD = 0;
    private static final String CREATE = "C";
    private static final String DRAW_LINE = "L";
    private static final String DRAW_RECT = "R";
    private static final int QUIT_TOKEN_COUNT = 1;
    private static final int X1 = 1;
    private static final int Y1 = 2;
    private static final int X2 = 3;
    private static final int Y2 = 4;
    private static final int DRAW_TOKEN_COUNT = 5;
    private static final int CREATE_TOKEN_COUNT = 3;
    private static final int CANVAS_WIDTH = 1;
    private static final int CANVAS_HEIGHT = 2;
    private static final String QUIT = "Q";

    private final Publisher<StateEvent> stateEventpublisher;
    private final Publisher<GraphicsEvent> graphicsEventPublisher;
    private final Publisher<DrawEvent> drawEventPublisher;

    public InputListener(Publisher<StateEvent> stateEventPublisher,
                         Publisher<GraphicsEvent> graphicsEventPublisher,
                         Publisher<DrawEvent> drawEventPublisher){
        this.stateEventpublisher = stateEventPublisher;
        this.graphicsEventPublisher = graphicsEventPublisher;
        this.drawEventPublisher = drawEventPublisher;
    }

    public void onInputEvent(Event event) {
        InputEvent inputEvent = (InputEvent) event;
        interpretAndPublish(inputEvent);
    }

    private boolean isValid(List<String> tokens) {
        return tokens.size() > 0;
    }

    public  void interpretAndPublish(InputEvent event) {
        String input = event.getInput().toUpperCase();

        List<String> tokens = Arrays.stream(input.split(" "))
                .filter(x->x.length() != 0)
                .collect(Collectors.toList());
        if(!isValid(tokens)) {
            return;
        }
        if(tokens.get(CMD).equals(CREATE)) {
            interpretCreate(tokens);
        } else if(tokens.get(CMD).equals(DRAW_LINE)) {
            interpretDraw(tokens, LineDrawEvent.builder(), Graphics.LINE_ERR);
        } else if(tokens.get(CMD).equals(DRAW_RECT)) {
            interpretDraw(tokens, RectDrawEvent.builder(), Graphics.RECT_ERR);
        } else if(tokens.get(CMD).equals(QUIT)) {
            interpretQuit(tokens);
        } else {
            graphicsEventPublisher.publish(MessageGraphicsEvent.builder()
                    .message(Graphics.UNKNOWN_COMMAND)
                    .build());
        }
    }

    private void interpretQuit(List<String> tokens) {
        if(tokens.size() == QUIT_TOKEN_COUNT) {
            stateEventpublisher.publish(new QuitStateEvent());
        } else {
            graphicsEventPublisher.publish(MessageGraphicsEvent.builder()
                    .message(Graphics.QUIT_ERR)
                    .build());
        }
    }

    private void interpretDraw(List<String> tokens, DrawEvent.DrawEventBuilder builder, String message) {
        try {
            if(hasValidTokenCount(tokens, DRAW_TOKEN_COUNT)) {
                throw new IllegalArgumentException(message);
            }
            DrawEvent event = builder
                    .colStart(Integer.valueOf(tokens.get(X1)) - 1)
                    .rowStart(Integer.valueOf(tokens.get(Y1)) - 1)
                    .colEnd(Integer.valueOf(tokens.get(X2)) - 1)
                    .rowEnd(Integer.valueOf(tokens.get(Y2)) - 1)
                    .build();
            drawEventPublisher.publish(event);
        } catch (ArrayIndexOutOfBoundsException e){
            graphicsEventPublisher.publish(MessageGraphicsEvent.builder()
                    .message(message)
                    .build());
        } catch(IllegalArgumentException e) {
            graphicsEventPublisher.publish(MessageGraphicsEvent.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    private boolean hasValidTokenCount(List<String> tokens, int tokenCount) {
        return tokens.size() != tokenCount;
    }


    private void interpretCreate(List<String> tokens) {
        try {
            if(hasValidTokenCount(tokens, CREATE_TOKEN_COUNT)) {
                throw new IllegalArgumentException();
            }

            StateEvent event = CreateStateEvent.builder()
                    .width(Integer.valueOf(tokens.get(CANVAS_WIDTH)))
                    .height(Integer.valueOf(tokens.get(CANVAS_HEIGHT)))
                    .build();
            stateEventpublisher.publish(event);
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e){
            graphicsEventPublisher.publish(MessageGraphicsEvent.builder()
                    .message(Graphics.CREATE_ERR)
                    .build());
        }
    }

}
