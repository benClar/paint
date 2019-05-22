package drawing.event;

public abstract class DrawEvent implements Event{
    private final int rowStart;
    private final int colStart;
    private final int rowEnd;
    private final int colEnd;


    protected DrawEvent(int rowStart, int colStart, int rowEnd, int colEnd){
        this.rowStart = rowStart;
        this.colStart = colStart;
        this.rowEnd = rowEnd;
        this.colEnd = colEnd;

    }



    public int getRowStart() {
        return rowStart;
    }

    public int getColStart() {
        return colStart;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public int getColEnd() {
        return colEnd;
    }

    public abstract static class DrawEventBuilder implements EventBuilder<DrawEvent> {

        int rowStart;
        int rowEnd;
        int colStart;
        int colEnd;


        public DrawEventBuilder rowStart(int rowStart) {
            if(rowStart < 0) {
                throw new IllegalArgumentException("Y must be within boundaries of canvas");
            }
            this.rowStart = rowStart;
            return this;
        }

        public DrawEventBuilder rowEnd(int rowEnd) {
            if(rowEnd < 0) {
                throw new IllegalArgumentException("Y must be within boundaries of canvas");
            }
            this.rowEnd = rowEnd;
            return this;
        }

        public DrawEventBuilder colStart(int colStart) {
            if(colStart < 0) {
                throw new IllegalArgumentException("Col must be within boundaries of canvas");
            }

            this.colStart = colStart;
            return this;
        }

        public DrawEventBuilder colEnd(int colEnd) {
            if(colEnd < 0) {
                throw new IllegalArgumentException("Col must be within boundaries of canvas");
            }

            this.colEnd = colEnd;
            return this;
        }
    }




}
