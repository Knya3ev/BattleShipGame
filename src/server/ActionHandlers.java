package server;

import server.state.Command;
import constants.game.TableCellValue;
import server.setting.TableSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ActionHandlers {
    int beginningOfTheColumn = TableSetting.BEGINNING_OF_THE_COLUMN.getValue();
    int endOfTheColumn = TableSetting.END_OF_THE_COLUMN.getValue();
    int stepInTheColumn = TableSetting.STEP_OF_THE_COLUMN.getValue();

    PlayerService playerService;

    ActionHandlers(PlayerService playerService) {
        this.playerService = playerService;
    }


    public ArrayList<int[]> moveShips(String command, int[] shipX, int[] shipY) {

        int lengthShip = playerService.getShipCurrentPLayer();

        ArrayList<int[]> ships = new ArrayList<int[]>();
        if (lengthShip - 1 < 0) return ships;

        if (Command.LEFT.getSelfString().equals(command) && shipX[0] > beginningOfTheColumn)
            for (int i = 0; i < lengthShip; i++) shipX[i] -= stepInTheColumn;

        if (Command.RIGHT.getSelfString().equals(command) && shipX[0] < endOfTheColumn - 1 && shipX[lengthShip - 1] < endOfTheColumn)
            for (int i = 0; i < lengthShip; i++) shipX[i] += stepInTheColumn;

        if (Command.UP.getSelfString().equals(command) && shipY[0] > beginningOfTheColumn && shipY[lengthShip - 1] > beginningOfTheColumn)
            for (int i = 0; i < lengthShip; i++) shipY[i] -= stepInTheColumn;

        if (Command.DOWN.getSelfString().equals(command) && shipY[lengthShip - 1] < endOfTheColumn - 1 && shipY[0] < endOfTheColumn)
            for (int i = 0; i < lengthShip; i++) shipY[i] += stepInTheColumn;

        if (Command.ENTER.getSelfString().equals(command)) {
            if (checkCollision(shipX, shipY)) {
                playerService.writeShip(shipX, shipY);
                playerService.nextShipCurrentPlayer();
                for (int i = 0; i < shipX.length; i++) {
                    shipY[i] = 0;
                    shipX[i] = 0;
                }
                for (int i = 0; i < lengthShip; i++) shipY[i] = i;
            }

        }

        if (Command.SPACE.getSelfString().equals(command)) {
            for (int i = 1; i < lengthShip; i++) {
                if (shipX[0] == shipX[lengthShip - 1]) {
                    shipY[i] = shipY[0];
                    shipX[i] = shipX[0] + (i * stepInTheColumn);
                    if (shipX[i] + stepInTheColumn > endOfTheColumn)
                        for (int j = lengthShip - 1; j != -1; j--) shipX[j] -= stepInTheColumn;

                } else {
                    shipX[i] = shipX[0];
                    shipY[i] = shipY[0] + (i * stepInTheColumn);
                    if (shipY[i] + stepInTheColumn > endOfTheColumn)
                        for (int j = lengthShip - 1; j != -1; j--) shipY[j] -= stepInTheColumn;
                }
            }
        }
        ships.add(0, shipX);
        ships.add(1, shipY);
        return ships;

    }


    private boolean checkCollision(int[] x, int[] y) {
        int endShipIndex = playerService.getShipCurrentPLayer() - 1;
//        if (x[0] == 0) return false;
        for (int i = 0; i < endShipIndex; i++)
            if (playerService.checkShipInTheCurrentPlayerMap(x[i], y[i])) return false;
        if (x[0] == x[endShipIndex]) {
            for (int i = y[0] - stepInTheColumn; i < y[endShipIndex] + stepInTheColumn; i++) {
                if (playerService.checkShipInTheCurrentPlayerMap(x[0] - stepInTheColumn, i)) return false;
                if (playerService.checkShipInTheCurrentPlayerMap(x[0] + stepInTheColumn, i)) return false;
            }
            if (playerService.checkShipInTheCurrentPlayerMap(x[0], y[0] - stepInTheColumn)) return false;
            if (playerService.checkShipInTheCurrentPlayerMap(x[0], y[endShipIndex] + stepInTheColumn)) return false;
        }
        if (y[0] == y[endShipIndex]) {
            for (int i = x[0] - stepInTheColumn; i < x[endShipIndex] + stepInTheColumn; i++) {
                if (playerService.checkShipInTheCurrentPlayerMap(i, y[0] - stepInTheColumn)) return false;
                if (playerService.checkShipInTheCurrentPlayerMap(i, y[0] + stepInTheColumn)) return false;
            }
            if (playerService.checkShipInTheCurrentPlayerMap(x[0] - stepInTheColumn, y[0])) return false;
            if (playerService.checkShipInTheCurrentPlayerMap(x[endShipIndex] + stepInTheColumn, y[0])) return false;
        }
        return true;
    }

    public void checkDeadShip(int x, int y) {
        ArrayList<ArrayList<Integer>> list = searchIsKill(x, y, playerService.getOpponent().getMapToPlayer());
        if (!list.isEmpty()) {

            List<Integer> xList = list.get(0).stream().sorted().collect(Collectors.toList());
            List<Integer> yList = list.get(1).stream().sorted().collect(Collectors.toList());

            if (xList.get(0).equals(xList.get(xList.size() - 1))) {
                for (int i = yList.get(0) - stepInTheColumn; i < yList.get(yList.size() - 1) + (stepInTheColumn * 2); i++) {
                    playerService.writeHitOrMissPlayers(xList.get(0) - stepInTheColumn, i, false);
                    playerService.writeHitOrMissPlayers(xList.get(0) + stepInTheColumn, i, false);
                }
                playerService.writeHitOrMissPlayers(xList.get(0), yList.get(0) - stepInTheColumn, false);
                playerService.writeHitOrMissPlayers(xList.get(0), yList.get(yList.size() - 1) + stepInTheColumn, false);
            }
            if (yList.get(0).equals(yList.get(yList.size() - stepInTheColumn))) {
                for (int i = xList.get(0) - stepInTheColumn; i < xList.get(yList.size() - 1) + (stepInTheColumn * 2); i++) {
                    playerService.writeHitOrMissPlayers(i, yList.get(0) - stepInTheColumn, false);
                    playerService.writeHitOrMissPlayers(i, yList.get(0) + stepInTheColumn, false);
                }
                playerService.writeHitOrMissPlayers(xList.get(0) - stepInTheColumn, yList.get(0), false);
                playerService.writeHitOrMissPlayers(xList.get(xList.size() - 1) + stepInTheColumn, yList.get(0), false);
            }

        }

    }


    private ArrayList<ArrayList<Integer>> searchIsKill(int x, int y, Map<Integer, Map<Integer, Integer>> opponentMap) {

        int content = playerService.getContentBoxInThePlayer(x, y, opponentMap);
        ArrayList<ArrayList<Integer>> resaltList = new ArrayList<>();
        ArrayList<Integer> contentList = new ArrayList<>();
        ArrayList<Integer> xList = new ArrayList<>();
        ArrayList<Integer> yList = new ArrayList<>();

        if (content != 3 && content != 0) {
            xList.add(x);
            yList.add(y);
            for (int i = y - stepInTheColumn; i > beginningOfTheColumn - stepInTheColumn; i--) {  //move to up
                content = playerService.getContentBoxInThePlayer(x, i, opponentMap);
                if (content == 0 || content == 3) break;
                else {
                    contentList.add(content);
                    xList.add(x);
                    yList.add(i);
                }
            }
            for (int i = y + stepInTheColumn; i < endOfTheColumn; i++) { //move to down
                content = playerService.getContentBoxInThePlayer(x, i, opponentMap);
                if (content == 0 || content == 3) break;
                else {
                    contentList.add(content);
                    xList.add(x);
                    yList.add(i);
                }
            }
            for (int i = x - stepInTheColumn; i > beginningOfTheColumn - stepInTheColumn; i--) { //move to left
                content = playerService.getContentBoxInThePlayer(i, y, opponentMap);
                if (content == 0 || content == 3) break;
                else {
                    contentList.add(content);
                    xList.add(i);
                    yList.add(y);
                }
            }
            for (int i = x + stepInTheColumn; i < endOfTheColumn; i++) {  //move to right
                content = playerService.getContentBoxInThePlayer(i, y, opponentMap);
                if (content == 0 || content == 3) break;
                else {
                    contentList.add(content);
                    xList.add(i);
                    yList.add(y);
                }
            }


            boolean isKilling = contentList.stream().allMatch(s -> s == TableCellValue.IS_HIT.ordinal());
            if (isKilling) {
                resaltList.add(xList);
                resaltList.add(yList);
            }
        }
        return resaltList;
    }

    public int[] moveCursor(String command, int x, int y) {
        if (Command.LEFT.getSelfString().equals(command) && x > beginningOfTheColumn) x -= stepInTheColumn;
        if (Command.RIGHT.getSelfString().equals(command) && x < endOfTheColumn - 1) x += stepInTheColumn;
        if (Command.UP.getSelfString().equals(command) && y > beginningOfTheColumn) y -= stepInTheColumn;
        if (Command.DOWN.getSelfString().equals(command) && y < endOfTheColumn - 1) y += stepInTheColumn;

        return new int[]{x, y};
    }

}
