package drawing.publisher;

public interface Publisher<E> {

    void publish(E event);

}
