package drawing.canvas;

public class CanvasCoordinate {
    private final int row;
    private final int col;

    public CanvasCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
