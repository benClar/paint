package drawing.event;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CreateStateEventTest {

    @Test
    public void testCreateStateEvent(){
        CreateStateEvent event = CreateStateEvent.builder().height(20).width(10).build();
        assertEquals(20,event.getHeight());
        assertEquals(10, event.getWidth());
        assertEquals(EventType.CREATE, event.getType());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidHeight(){
        CreateStateEvent.builder().height(0).width(10).build();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidWidth(){
        CreateStateEvent.builder().height(20).width(-99).build();
    }

}
