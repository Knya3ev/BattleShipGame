package constants.graphics;

import javax.swing.*;
import java.awt.*;

public class StartGameConstants {
    public static final Font FONT = new Font("Arial", 1, 30);
    public static final ImageIcon imageShip = new ImageIcon("resources/StartGameShip.png");
    public static int IMAGE_SHIP_LEFT_MARGIN = 240;
    public static int IMAGE_SHIP_TOP_MARGIN = 20;
    public static int IMAGE_SHIP_HEIGHT = 140;
    public static final int IMAGE_SHIP_WIDTH = (int) (IMAGE_SHIP_HEIGHT * 2.3);
    public static final int STRING_BATTLE_SHIP_LEFT_MARGIN = 320;
    public static final int STRING_BATTLE_SHIP_TOP_MARGIN = 200;

    public static final int STRING_START_LEFT_MARGIN = 350;
    public static final int STRING_START_TOP_MARGIN = 280;

    public static final int STRING_EXIT_LEFT_MARGIN = 350;
    public static final int STRING_EXIT_TOP_MARGIN = 320;

    public static Image getImageShip() {
        return imageShip.getImage();
    }
}
