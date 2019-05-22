package drawing.canvas;

public abstract class Canvas {

    Canvas(int width, int height){
        this.width = width;
        this.height = height;
    }

    private final int height;
    private final int width;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract int get(int row, int col);
}
