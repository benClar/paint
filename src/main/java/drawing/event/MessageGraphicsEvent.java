package drawing.event;

public class MessageGraphicsEvent extends GraphicsEvent {

    private final String message;
    private final EventType type = EventType.MESSAGE;

    private MessageGraphicsEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public static class MessageGraphicsEventBuilder {
        private String message;

        private MessageGraphicsEventBuilder(){

        }

        public MessageGraphicsEventBuilder message(String message) {
            if(message.length() <= 0) {
                throw new IllegalArgumentException("Blank messages are not allowed");
            }
            this.message = message;
            return this;
        }

        public MessageGraphicsEvent build(){
            return new MessageGraphicsEvent(message);
        }
    }

    public EventType getType(){
        return type;
    }

    public static MessageGraphicsEventBuilder builder(){
        return new MessageGraphicsEventBuilder();
    }
}
