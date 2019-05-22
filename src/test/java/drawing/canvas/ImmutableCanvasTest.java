package drawing.canvas;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ImmutableCanvasTest {

    @Test
    public void testCanvasCreate(){
        MutableCanvas canvas = new MutableCanvas(5, 10);
        canvas.set(0, 1, 1);
        canvas.set(0, 2, 1);
        canvas.set(0, 3, 1);
        ImmutableCanvas iCanvas = new ImmutableCanvas(canvas);
        for(int row = 0; row < canvas.getHeight(); row++){
            for(int column = 0; column < canvas.getWidth(); column++){
                assertEquals(canvas.get(row, column), iCanvas.get(row, column));
            }
        }
    }
}