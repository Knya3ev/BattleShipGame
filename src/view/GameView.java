package view;

import communication.*;
import constants.graphics.GameOverConstants;
import graphics.GameGraphics;
import server.GameService;
import server.state.StateGameBody;
import server.state.StateGameHead;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class GameView extends JPanel implements ActionListener {
    private final int STEP_PIXEL_FOR_GAME = 28;
    private final GameGraphics gameGraphics = new GameGraphics(STEP_PIXEL_FOR_GAME);
    private final TranslateForServerOrView translate = new TranslateForServerOrView(STEP_PIXEL_FOR_GAME);
    private Chat.Request request = new Chat.Request(translate);
    private Chat.Response response = new Chat.Response(translate);
    private GameService service = new GameService(response);
    private boolean lock = false;
    private int cursorX = 310;
    private int cursorY = 250;

    public GameView() {
        setBackground(Color.BLACK);
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
        Timer timer = new Timer(250, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        sendRequest(request);
        if (!lock) {
            if (StateGameHead.START.isState()) startGame(g);
            if (StateGameHead.CREATE_PLAYER.isState()) createPlayer(g);
            if (StateGameHead.BATTLE.isState()) battle(g);
            if (StateGameHead.GAME_OVER.isState()) gameOver(g);
        }
    }

    private void startGame(Graphics g) {
        gameGraphics.startGame(g, cursorX, cursorY);
    }

    private void createPlayer(Graphics g) {
        gameGraphics.createPlayer(g, response);
    }

    private void battle(Graphics g) {
        request.setHEAD(StateGameHead.BATTLE.getStateName());
        gameGraphics.battleGame(g, response, cursorX, cursorY);
    }

    private void gameOver(Graphics g) {
        if (cursorX != GameOverConstants.CURSOR_COORDINATE_BY_X) {
            cursorX = GameOverConstants.CURSOR_COORDINATE_BY_X;
            cursorY = GameOverConstants.CURSOR_COORDINATE_BY_Y;
        }
        gameGraphics.endGame(g, response.getName(), response.getPlayerMap(), cursorX, cursorY);
    }

    private void sendRequest(Chat.Request request) {
        lock = true;
        response = service.listener(request);
        lock = false;
    }

    private void move(String command) {
        request.setMoveCommand(command);
        request.setBODY(StateGameBody.MOVE.getName());
        request.setArrayShip(response.getArrayShip("x"), response.getArrayShip("y"));
    }

    private void moveCursor(String command) {
        request.setMoveCommand(command);
        request.setBODY(StateGameBody.MOVE.getName());
        request.setCursor(cursorX, cursorY);
        sendRequest(request);
        updateCursor();
    }

    private void updateCursor() {
        try {
            cursorX = response.getCursor("x");
            cursorY = response.getCursor("y");

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    private void attack(int x, int y) {
        request.setBODY(StateGameBody.ATTACK.getName());
        request.setAttack(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }


    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (StateGameHead.START.isState()) {
                if (key == KeyEvent.VK_DOWN && cursorY < 290) cursorY += 40;
                if (key == KeyEvent.VK_UP && cursorY > 250) cursorY -= 40;
                if (key == KeyEvent.VK_ENTER && cursorY == 290) System.exit(0);
                if (key == KeyEvent.VK_ENTER && cursorY == 250) {
                    request.setHEAD(StateGameHead.START.getStateName());
                    cursorX = 152;
                    cursorY = 137;
                    lock = true;
                }
            }
            if (StateGameHead.CREATE_PLAYER.isState()) {
                if (key == KeyEvent.VK_RIGHT) move("right");
                if (key == KeyEvent.VK_LEFT) move("left");
                if (key == KeyEvent.VK_UP) move("up");
                if (key == KeyEvent.VK_DOWN) move("down");
                if (key == KeyEvent.VK_SPACE) move("space");
                if (key == KeyEvent.VK_ENTER) move("enter");
            }
            if (StateGameHead.BATTLE.isState()) {
                if (key == KeyEvent.VK_SPACE) {
                    request.setBODY(StateGameBody.PUSH_SPACE_BAR.getName());
                }
                if (key == KeyEvent.VK_RIGHT) moveCursor("right");
                if (key == KeyEvent.VK_LEFT) moveCursor("left");
                if (key == KeyEvent.VK_UP) moveCursor("up");
                if (key == KeyEvent.VK_DOWN) moveCursor("down");

                if (key == KeyEvent.VK_ENTER) attack(cursorX, cursorY);
            }
            if (StateGameHead.GAME_OVER.isState()) {
                if (key == KeyEvent.VK_UP && cursorY > GameOverConstants.TOP_MARGIN_FOR_STRING_RESTART - STEP_PIXEL_FOR_GAME)
                    cursorY = cursorY + GameOverConstants.CURSOR_STEP;
                if (key == KeyEvent.VK_DOWN && cursorY < GameOverConstants.TOP_MARGIN_FOR_STRING_EXIT - STEP_PIXEL_FOR_GAME)
                    cursorY = cursorY - GameOverConstants.CURSOR_STEP;
                if (key == KeyEvent.VK_ENTER && cursorY == GameOverConstants.TOP_MARGIN_FOR_STRING_RESTART - STEP_PIXEL_FOR_GAME) {
                    request.setBODY(StateGameBody.RESTART.getName());
                    sendRequest(request);
                    StateGameHead.GAME_OVER.setState(false);
                    StateGameHead.START.setState(true);
                    cursorX = 310;
                    cursorY = 250;
                    request.setHEAD("empty");

                }
                if (key == KeyEvent.VK_ENTER && cursorY == GameOverConstants.TOP_MARGIN_FOR_STRING_EXIT - STEP_PIXEL_FOR_GAME)
                    System.exit(0);
            }
        }

    }
}

