package constants.graphics;

import java.awt.*;

public class GameOverConstants extends TableConstants {
    public static final Font FONT = new Font("Arial", 10, 30);

    public static final int LEFT_MARGIN_FOR_STRING_WINNER = 450;
    public static final int TOP_MARGIN_FOR_STRING_WINNER = 50;

    public static final int LEFT_MARGIN_FOR_STRING_RESTART = 450;
    public static final int TOP_MARGIN_FOR_STRING_RESTART = 250;

    public static final int LEFT_MARGIN_FOR_STRING_EXIT = 450;
    public static final int TOP_MARGIN_FOR_STRING_EXIT = 300;

    public static final int CURSOR_COORDINATE_BY_X = LEFT_MARGIN_FOR_STRING_RESTART - 40;
    public static final int CURSOR_COORDINATE_BY_Y = TOP_MARGIN_FOR_STRING_RESTART - 28;
    public static final int CURSOR_STEP = TOP_MARGIN_FOR_STRING_RESTART-TOP_MARGIN_FOR_STRING_EXIT;
}
