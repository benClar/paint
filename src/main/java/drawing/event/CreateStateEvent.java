package drawing.event;

public class CreateStateEvent extends StateEvent {
    private final int width;
    private final int height;
    private final EventType type = EventType.CREATE;

    private CreateStateEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public EventType getType() {
        return type;
    }

    public static class CreateStateEventBuilder  implements EventBuilder<CreateStateEvent> {

        private int width;
        private int height;

        @Override
        public CreateStateEvent build(){
            return new CreateStateEvent(width, height);
        }

        public CreateStateEventBuilder width(int width){
            if(width <= 0 ){
                throw new IllegalArgumentException("width of canvas must be greater than 0");
            }
            this.width = width;
            return this;
        }

        public CreateStateEventBuilder height(int height){
            if(height <= 0 ){
                throw new IllegalArgumentException("Height of canvas must be greater than 0");
            }
            this.height = height;
            return this;
        }
    }

    public static CreateStateEventBuilder builder() {
        return new CreateStateEventBuilder();
    }

}
