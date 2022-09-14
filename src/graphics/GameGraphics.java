package graphics;

import communication.Chat;
import constants.game.CharForTableGame;
import constants.game.TableCellValue;
import constants.graphics.*;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Map;

public class GameGraphics implements ImageObserver {
    private final int stepForTable;
    private final String[] charsForInTheTable = CharForTableGame.getRuCharForTable();
    private final String[] numbersForInTheTable = CharForTableGame.getNumberCharForTable();
    private final ImagesGame imagesGame = new ImagesGame();

    public GameGraphics(int stepForTable) {
        this.stepForTable = stepForTable;
    }

    public void startGame(Graphics g, int x, int y) {
        g.setFont(StartGameConstants.FONT);
        g.setColor(Color.white);
        g.drawImage(
                StartGameConstants.getImageShip(),
                StartGameConstants.IMAGE_SHIP_LEFT_MARGIN,
                StartGameConstants.IMAGE_SHIP_TOP_MARGIN,
                StartGameConstants.IMAGE_SHIP_WIDTH,
                StartGameConstants.IMAGE_SHIP_HEIGHT,
                this);

        g.drawString(
                "BattleShip",
                StartGameConstants.STRING_BATTLE_SHIP_LEFT_MARGIN,
                StartGameConstants.STRING_BATTLE_SHIP_TOP_MARGIN);
        g.drawString(
                "Start",
                StartGameConstants.STRING_START_LEFT_MARGIN,
                StartGameConstants.STRING_START_TOP_MARGIN);
        g.drawString(
                "Exit",
                StartGameConstants.STRING_EXIT_LEFT_MARGIN,
                StartGameConstants.STRING_EXIT_TOP_MARGIN);

        drawAim(g, x, y);
    }

    public void createPlayer(Graphics g, Chat.Response response) {
        drawTable(g, response.getPlayerMap(), false);
        drawShip(g, response.getArrayShip("x"), response.getArrayShip("y"), response.getShip());
        showCountShips(g, response.getName(), response.getShipsNumbers());
    }

    public void battleGame(Graphics g, Chat.Response response, int cursorX, int cursorY) {
        if (!response.isOnlyPlayer()) standbyScreen(g, response.getName());
        else {
            drawTable(g, response.getOpponentMap(), false);
            drawTable(g, response.getPlayerMap(), true);
            infoInGame(g, response.getNameOpponent(), response.getAllShips());
            drawAim(g, cursorX, cursorY);
        }
    }

    public void endGame(Graphics g, String name,Map<Integer, Map<Integer, Integer>> map, int aimX, int aimY) {
        drawTable(g,map,false);

        g.setFont(GameOverConstants.FONT);
        g.setColor(Color.GREEN);

        g.drawString(
                name +" won!!!",
                GameOverConstants.LEFT_MARGIN_FOR_STRING_WINNER,
                GameOverConstants.TOP_MARGIN_FOR_STRING_WINNER);

        g.setColor(Color.white);

        g.drawString(
                "restart",
                GameOverConstants.LEFT_MARGIN_FOR_STRING_RESTART,
                GameOverConstants.TOP_MARGIN_FOR_STRING_RESTART);
        g.drawString(
                "exit",
                GameOverConstants.LEFT_MARGIN_FOR_STRING_EXIT,
                GameOverConstants.TOP_MARGIN_FOR_STRING_EXIT);

        drawAim(g, aimX, aimY);

    }


    public void drawAim(Graphics g, int x, int y) {
        g.drawImage(imagesGame.getAim(), x, y, this);
    }

    private void drawTable(Graphics g, Map<Integer, Map<Integer, Integer>> map, boolean isSecond) {
        paintCharAndNumbersInTheTable(g, isSecond);
        renderTable(g, map, isSecond);
    }

    private void paintCharAndNumbersInTheTable(Graphics g, boolean isSecondTable) {
        int x = TableConstants.LEFT_MARGIN_FOR_CHAR_IN_THE_TABLE;
        int y = TableConstants.LEFT_MARGIN_FOR_NUMBERS_IN_THE_TABLE;

        if (isSecondTable) for (int num : new int[]{x, y}) num += TableConstants.STEP_FOR_SECOND_TABLE;

        g.setColor(Color.white);

        for (int i = 0; i < charsForInTheTable.length; i++)
            g.drawString(charsForInTheTable[i], x + (i * stepForTable), TableConstants.TOP_MARGIN_FOR_CHAR_IN_THE_TABLE);
        for (int i = 0; i < numbersForInTheTable.length; i++)
            g.drawString(numbersForInTheTable[i], y, TableConstants.TOP_MARGIN_FOR_NUMBERS_IN_THE_TABLE + (i * stepForTable));
    }

    private void renderTable(Graphics g, Map<Integer, Map<Integer, Integer>> map, boolean isSecondTable) {
        int step = TableConstants.LEFT_MARGIN_FOR_RENDER_IN_THE_TABLE;

        if (isSecondTable) step += TableConstants.STEP_FOR_SECOND_TABLE;
        try {
            for (int i = 0; i < numbersForInTheTable.length; i++) {
                for (int j = 0; j < charsForInTheTable.length; j++) {
                    g.drawImage(
                            imagesGame.getRenderBox(map.get(j).get(i)),
                            step + (j * stepForTable),
                            TableConstants.TOP_MARGIN_FOR_RENDER_IN_THE_TABLE + (i * stepForTable),
                            this);
                }
            }
        } catch (NullPointerException s) {
            System.out.println(s.getMessage());
        }
    }

    public void standbyScreen(Graphics g, String name) {
        g.setFont(StandbyScreenConstants.FONT);
        g.setColor(Color.white);
        g.drawString(
                "It's " + name + "'s turn",
                StandbyScreenConstants.LEFT_MARGIN_FOR_THE_INSCRIPTION_WHOSE_TURN_IT_IS_TO_WALK,
                StandbyScreenConstants.TOP_MARGIN_FOR_THE_INSCRIPTION_WHOSE_TURN_IT_IS_TO_WALK);
        g.setColor(Color.GRAY);
        g.drawString(
                "Press the space bar to continue",
                StandbyScreenConstants.LEFT_MARGIN_FOR_THE_INSPECTION_PRESS_THE_SPACE_BAR,
                StandbyScreenConstants.TOP_MARGIN_FOR_THE_INSPECTION_PRESS_THE_SPACE_BAR);
    }

    public void infoInGame(Graphics g, String nameOpponent, int allShipsOpponent) {

        g.setColor(Color.white);
        g.drawString(
                "Your Ships",
                BattleConstants.LEFT_MARGIN_FOR_THE_STRING_YOUR_SHIPS,
                BattleConstants.TOP_MARGIN_FOR_THE_STRING_YOUR_SHIPS);
        g.drawString(
                "number of active decks of vessels  " + nameOpponent + "   :" + allShipsOpponent,
                BattleConstants.LEFT_MARGIN_FOR_NUMBER_SHIPS_THE_OPPONENT,
                BattleConstants.TOP_MARGIN_FOR_NUMBER_SHIPS_THE_OPPONENT);
    }

    private void showCountShips(Graphics g, String name, int[] numberShips) {
        int countShips = 4;
        int distanceBetweenImages = CreatePayerConstants.DISTANCE_BETWEEN_IMAGES;
        g.setFont(CreatePayerConstants.FONT_BASE);
        g.setColor(Color.white);

        for (int i = countShips; i != 0; i--) {
            for (int j = i; j != 0; j--) {
                g.drawImage(imagesGame.getRenderBox(TableCellValue.IS_SHIP.ordinal()),
                        CreatePayerConstants.LEFT_MARGIN_FOR_IMAGE_SHIP + (j * stepForTable),
                        CreatePayerConstants.TOP_MARGIN_FOR_IMAGE_SHIP + (i * distanceBetweenImages),
                        this);
            }
            g.drawString(
                    Integer.toString(numberShips[i - 1]) + " X",
                    CreatePayerConstants.LEFT_MARGIN_STRING_FOR_COUNT_SHIPS,
                    CreatePayerConstants.TOP_MARGIN_STRING_FOR_COUNT_SHIPS + (i * distanceBetweenImages));
        }
        g.setFont(CreatePayerConstants.FONT_NAME);
        g.setColor(Color.yellow);
        g.drawString(name, CreatePayerConstants.LEFT_MARGIN_FOR_STRING_NAME_PLAYER,
                CreatePayerConstants.TOP_MARGIN_FOR_STRING_NAME_PLAYER);
    }

    public void drawShip(Graphics g, int[] x, int[] y, int length) {
        for (int i = 0; i < length; i++) {
            g.drawImage(imagesGame.getRenderBox(TableCellValue.IS_SHIP.ordinal()), x[i], y[i], this);
        }
    }


    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}
