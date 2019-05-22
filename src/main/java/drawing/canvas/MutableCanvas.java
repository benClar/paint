package drawing.canvas;

public class MutableCanvas extends Canvas {
    private final int[][] canvas;

    public MutableCanvas(int width, int height) {
        super(width, height);
        this.canvas = new int[height][width];
    }

    public MutableCanvas(MutableCanvas otherCanvas) {
        super(otherCanvas.getWidth(), otherCanvas.getHeight());
        this.canvas = new int[getHeight()][getWidth()];
        for(int r = 0; r < getHeight(); r++){
            for(int c = 0; c < getWidth(); c++){
                canvas[r][c] = otherCanvas.get(r, c);
            }
        }
    }

    public void set(int row, int col, int val) {
        canvas[row][col] = val;
    }

    public int get(int row, int col){
        return canvas[row][col];
    }
}
