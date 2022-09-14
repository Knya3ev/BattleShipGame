package server;

import java.util.Map;

public class Player {
    private String name;
    private int[] numberShips;
    private int stepIndexForShips;
    private final int[] ships = new int[]{3,4}; //new int[]{1, 1, 1, 1, 2, 2, 2, 3, 3, 4};
    private int numberOFAllShips;
    private int numberOfActions;
    private boolean onlyPlayer;
    private boolean thePlayerISReadyToFight;
    private Map<Integer, Map<Integer, Integer>> mapToPlayer;
    private Map<Integer, Map<Integer, Integer>> mapToOpponent;


    public Player(String name, int numberOfActions) {
        this.name = name;
        this.numberOfActions = numberOfActions;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getNumberShips() {
        return numberShips;
    }

    public void setNumberShips(int[] numberShips) {
        this.numberShips = numberShips;
    }

    public int getStepIndexForShips() {
        return stepIndexForShips;
    }

    public void setStepIndexForShips(int stepIndexForShips) {
        this.stepIndexForShips = stepIndexForShips;
    }

    public int[] getShips() {
        return ships;
    }

    public int getNumberOFAllShips() {
        return numberOFAllShips;
    }

    public void setNumberOFAllShips(int numberOFAllShips) {
        this.numberOFAllShips = numberOFAllShips;
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    public boolean isOnlyPlayer() {
        return onlyPlayer;
    }

    public void setOnlyPlayer(boolean onlyPlayer) {
        this.onlyPlayer = onlyPlayer;
    }

    public boolean isThePlayerISReadyToFight() {
        return thePlayerISReadyToFight;
    }

    public void setThePlayerISReadyToFight(boolean thePlayerISReadyToFight) {
        this.thePlayerISReadyToFight = thePlayerISReadyToFight;
    }

    public Map<Integer, Map<Integer, Integer>> getMapToPlayer() {
        return mapToPlayer;
    }

    public void setMapToPlayer(Map<Integer, Map<Integer, Integer>> mapToPlayer) {
        this.mapToPlayer = mapToPlayer;
    }

    public Map<Integer, Map<Integer, Integer>> getMapToOpponent() {
        return mapToOpponent;
    }

    public void setMapToOpponent(Map<Integer, Map<Integer, Integer>> mapToOpponent) {
        this.mapToOpponent = mapToOpponent;
    }
}

