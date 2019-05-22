package drawing.canvas;

public class ImmutableCanvas extends Canvas {
    private final int[][] canvas;

    public ImmutableCanvas(Canvas mCanvas) {
        super(mCanvas.getWidth(), mCanvas.getHeight());
        canvas = new int[mCanvas.getHeight()][mCanvas.getWidth()];
        for(int row = 0; row < mCanvas.getHeight(); row++){
            for(int col = 0; col < mCanvas.getWidth(); col++){
                canvas[row][col] = mCanvas.get(row, col);
            }
        }
    }

    @Override
    public int get(int row, int col) {
        return canvas[row][col];
    }
}
