package server;

import constants.game.TableCellValue;
import server.setting.TableSetting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class PlayerService {
    Player p1;
    Player p2;
    Player player;
    Player opponent;
    Player winner;

    PlayerService(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        initPLayers(new Player[]{this.p1, this.p2});
    }


    private void initPLayers(Player[] players) {
        for (Player player : players) {
            player.setNumberOFAllShips(Arrays.stream(player.getShips()).sum());
            player.setNumberShips(new int[]{4, 3, 2, 1});
            player.setStepIndexForShips(10);

            player.setOnlyPlayer(false);
            player.setThePlayerISReadyToFight(false);

            Map<Integer, Map<Integer, Integer>> playerMap = new HashMap<>();
            Map<Integer, Map<Integer, Integer>> opponentMap = new HashMap<>();

            for (int i = TableSetting.BEGINNING_OF_THE_COLUMN.getValue();    //Create table game
                 i < TableSetting.END_OF_THE_COLUMN.getValue(); i++) {

                Map<Integer, Integer> linePlayer = new HashMap<>();
                Map<Integer, Integer> lineOpponent = new HashMap<>();

                for (int j = TableSetting.BEGINNING_OF_THE_COLUMN.getValue();
                     j < TableSetting.END_OF_THE_COLUMN.getValue(); j++) {

                    linePlayer.put(j, TableCellValue.IS_EMPTY.ordinal());
                    lineOpponent.put(j, TableCellValue.IS_EMPTY.ordinal());
                }
                playerMap.put(i, linePlayer);
                opponentMap.put(i, lineOpponent);
            }
            player.setMapToPlayer(playerMap);
            player.setMapToOpponent(opponentMap);

        }
        updatePlayers();
    }


    private void updatePlayers() {
        player = p1.getNumberOfActions() > 0 ? p1 : p2;
        opponent = p2.getNumberOfActions() < 1 ? p2 : p1;
    }


    public boolean thereISaWinner() {
        if (p1.getNumberOFAllShips() == 0 || p2.getNumberOFAllShips() == 0) {
            winner = p1.getNumberOFAllShips() != 0 ? p1 : p2;
            return true;
        } else return false;

    }

    public void attack(int x, int y) {
        boolean isHit = checkShipInTheOpponentMap(x, y);
        writeHitOrMissPlayers(x, y, isHit);
        if (!isHit) {
            player.setOnlyPlayer(false);
            player.setNumberOfActions(0);
            opponent.setNumberOfActions(1);
        }
        updatePlayers();
    }

    void writeHitOrMissPlayers(int x, int y, boolean isHit) {
        if (checkEmptyBoxInTheOpponentMAp(x, y)) {
            if (isHit) opponent.setNumberOFAllShips(opponent.getNumberOFAllShips() - 1);
            int value = isHit ? TableCellValue.IS_HIT.ordinal() : TableCellValue.IS_MISS.ordinal();
            try {
                player.getMapToOpponent().get(x).put(y, value);
                opponent.getMapToPlayer().get(x).put(y, value);
            } catch (NullPointerException exs) {
                System.out.println(exs.getMessage());
            }
        }
    }

    public void writeShip(int[] x, int[] y) {
        Player currentPlayer = getThePlayerBeingCreated();
        if (currentPlayer != null) {
            for (int i = 0; i < getShipCurrentPLayer(); i++) {
                currentPlayer.getMapToPlayer().get(x[i]).put(y[i], TableCellValue.IS_SHIP.ordinal());
            }
        }
    }

    public int getShipCurrentPLayer() {
        Player p = getThePlayerBeingCreated();
        if (p != null) {
            int stepIndex = p.getStepIndexForShips();
            return stepIndex == 0 ? 0 : p.getShips()[stepIndex - 1];
        }
        return 0;
    }

    public void nextShipCurrentPlayer() {
        Player p = getThePlayerBeingCreated();
        if (p != null) {
            int stepIndex = p.getStepIndexForShips();
            int[] numberShips = p.getNumberShips();
            int ship = p.getShips()[stepIndex - 1];

            numberShips[ship - 1] -= 1;
            p.setNumberShips(numberShips);
            p.setStepIndexForShips(stepIndex - 1);
        }

    }

    public int getContentBoxInThePlayer(int x, int y, Map<Integer, Map<Integer, Integer>> map) {
        if (map.isEmpty()) return 0;
        else return map.get(x).get(y);
    }

    public Player getThePlayerBeingCreated() {
        return !p1.isThePlayerISReadyToFight() ? p1 : p2;
    }

    public boolean playersIsReady() {
        return p1.isThePlayerISReadyToFight() && p2.isThePlayerISReadyToFight();
    }

    public Player getPlayer() {
        return player;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Player getWinner() {
        return winner;
    }

    public void restartPLayers() {
        initPLayers(new Player[]{this.p1, this.p2});
    }

    private boolean checkEmptyBoxInTheOpponentMAp(int x, int y) {
        return _checkValueInTheMap(x, y, player.getMapToOpponent(), TableCellValue.IS_EMPTY.ordinal());
    }

    public boolean checkShipInTheOpponentMap(int x, int y) {
        return _checkValueInTheMap(x, y, opponent.getMapToPlayer(), TableCellValue.IS_SHIP.ordinal());
    }

    public boolean checkShipInTheCurrentPlayerMap(int x, int y) {
        Map<Integer, Map<Integer, Integer>> map = getThePlayerBeingCreated().getMapToPlayer();
        return _checkValueInTheMap(x, y, map, TableCellValue.IS_SHIP.ordinal());
    }

    private boolean _checkValueInTheMap(int x, int y, Map<Integer, Map<Integer, Integer>> map, int cellValue) {
        try {
            if (map.isEmpty()) return false;
            else return map.get(x).get(y) == cellValue;
        } catch (NullPointerException e) {
            return false;
        }
    }


}



