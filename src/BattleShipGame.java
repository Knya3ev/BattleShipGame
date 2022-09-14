import constants.graphics.WindowConstants;
import view.GameView;

import javax.swing.*;

public class BattleShipGame extends JFrame {

    public BattleShipGame() {
        setTitle("Battle Ship");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WindowConstants.WIDTH_WINDOW_SIZE,
                WindowConstants.HEIGHT_WINDOW_SIZE);
        setLocation(
                WindowConstants.DISPLAYING_WINDOW_BY_X,
                WindowConstants.DISPLAYING_WINDOW_BY_Y);
        add(new GameView());
        setVisible(true);
    }

    public static void main(String[] args) {

        BattleShipGame mw = new BattleShipGame();
    }
}
