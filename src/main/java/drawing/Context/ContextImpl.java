package drawing.Context;

import drawing.event.*;
import drawing.eventhub.EventHub;
import drawing.eventhub.EventHubImpl;
import drawing.eventhub.hubpublisher.EventHubPublisherImpl;
import drawing.listener.DrawEventListener;
import drawing.listener.GraphicsEventListener;
import drawing.listener.InterpreterListener;
import drawing.listener.StateEventListener;
import drawing.plugin.KeyboardInputPlugin;
import drawing.plugin.Plugin;
import drawing.publisher.Publisher;
import drawing.publisher.PublisherImpl;
import drawing.util.Graphics;

public class ContextImpl implements Context {

    public void start() {
        EventHub eventHub = new EventHubImpl(new EventHubPublisherImpl());
        Publisher<StateEvent> stateEventPub = new PublisherImpl<>(eventHub, StateEvent.class);
        Publisher<GraphicsEvent> graphicsEventPub = new PublisherImpl<>(eventHub, GraphicsEvent.class);
        Publisher<InputEvent> inputEventPub = new PublisherImpl<>(eventHub, InputEvent.class);
        Publisher<DrawEvent> drawEventPub = new PublisherImpl<>(eventHub, DrawEvent.class);

        Plugin keyboardListenerPlugin = new KeyboardInputPlugin(inputEventPub);

        InterpreterListener inputInterpreter = new InterpreterListener(stateEventPub,
                graphicsEventPub,
                drawEventPub);
        StateEventListener stateEventListener = new StateEventListener(graphicsEventPub);
        GraphicsEventListener graphicsEventListener = new GraphicsEventListener();
        DrawEventListener drawEventListener = new DrawEventListener(stateEventPub, graphicsEventPub);

        eventHub.subscribe(StateEvent.class, stateEventListener::onStateEvent);
        eventHub.subscribe(InputEvent.class, inputInterpreter::onInputEvent);
        eventHub.subscribe(GraphicsEvent.class, graphicsEventListener::onGraphicsEvent);
        eventHub.subscribe(DrawEvent.class, drawEventListener::onDrawEvent);


        while(stateEventListener.shouldContinue()) {
            graphicsEventPub.publish(MessageGraphicsEvent
                    .builder()
                    .message(Graphics.PROMPT)
                    .build());
            keyboardListenerPlugin.exec();
        }
    }
}
