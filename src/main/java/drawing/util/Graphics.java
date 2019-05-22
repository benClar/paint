package drawing.util;

import java.util.Formatter;

public class Graphics {
    public static final String CREATE_ERR = "Invalid CREATE command.  Syntax is: \n C <NUM> <NUM>";
    public static final String LINE_ERR   = "Invalid LINE command.  Syntax is: \n L <x1> <y1> <x2> <y2>";
    public static final String RECT_ERR = "Invalid RECT command.  Syntax is: \n L <x1> <y1> <x2> <y2>";
    public static final String QUIT_ERR = "Invalid QUIT command.  Syntax is: \n Q";
    public static final String NOT_A_LINE_ERR = "Invalid LINE command.  x1 and x2 OR y1 and y2 must remain equal.\n";

    public static final String PROMPT = "Enter Command: ";

    private static final String COORD_ERR = "Invalid coord %s, %s.  Canvas is %s*%s.\n";
    public static final String[] paint = new String[]{" ", "X"};

    public static final String TOP_FRAME ="-";
    public static final String SIDE_FRAME ="|";

    public static final String UNKNOWN_COMMAND = "Unknown Command.\n";

    public static String coordErrorMessage(int invalidRow, int invalidCol, int canvasWidth, int canvasHeight){
        StringBuilder sbuf = new StringBuilder();
        Formatter fmt = new Formatter(sbuf);
        fmt.format(Graphics.COORD_ERR, invalidCol, invalidRow, canvasWidth, canvasHeight);
        return fmt.toString();
    }
}
