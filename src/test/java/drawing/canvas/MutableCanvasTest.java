package drawing.canvas;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MutableCanvasTest {

    @Test
    public void testMutableCanvasCreate(){
        Canvas canvas = new MutableCanvas(5, 10);
        for(int row = 0; row < canvas.getHeight(); row++){
            for(int column = 0; column < canvas.getWidth(); column++){
                assertEquals(canvas.get(row, column), 0);
            }
        }
    }

    @Test
    public void testMutableCanvasCreateFromCanvas(){
        MutableCanvas canvas = new MutableCanvas(5, 10);
        canvas.set(0, 1, 1);
        canvas.set(0, 2, 1);
        canvas.set(0, 3, 1);
        MutableCanvas newCanvas = new MutableCanvas(canvas);

        for(int row = 0; row < canvas.getHeight(); row++){
            for(int column = 0; column < canvas.getWidth(); column++){
                assertEquals(canvas.get(row, column), newCanvas.get(row, column));
            }
        }
    }
}
