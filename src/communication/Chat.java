package communication;

import java.util.HashMap;
import java.util.Map;

public class Chat extends CommunicationBetweenTheServerAndTheViewer {

    public static class Request extends CommunicationBetweenTheServerAndTheViewer.Request {

        public Request(TranslateForServerOrView translate) {
            super(translate);
            defaultRequest();
        }

        private void defaultRequest() {
            setArrayShip(new int[4], new int[4]);
            setAttack(0, 0);
            setMoveCommand("None");
            setBODY("None");
            setHEAD("None");
        }

        private Map<String, Object> CONTEXT = new HashMap<>();

        private Map<String, Object> getCONTEXT() {
            return CONTEXT;
        }


        public void setCursor(int x, int y) {
            int[] array = translate.getIndexesForServer(x, y);

            getCONTEXT().put("cursor x", array[0]);
            getCONTEXT().put("cursor y", array[1]);
        }

        public int getCursor(String byCoordinate) {
            return (int) (byCoordinate.equals("x") ? getCONTEXT().get("cursor x") : getCONTEXT().get("cursor y"));
        }

        public int[] getArrayShip(String byCoordinate) {
            return (int[]) (byCoordinate.equals("x") ? getCONTEXT().get("ship x") : getCONTEXT().get("ship y"));
        }

        public void setArrayShip(int[] shipX, int[] shipY) {
            int[][] array = translate.getShipIndexesForServer(shipX, shipY);
            getCONTEXT().put("ship x", array[0]);
            getCONTEXT().put("ship y", array[1]);
        }

        public int getAttack(String byCoordinate) {
            return (int) (byCoordinate.equals("x") ? getCONTEXT().get("attack x") : getCONTEXT().get("attack y"));
        }

        public void setAttack(int x, int y) {
//            System.out.println("SET ATTACK      x="+x+"   y="+y);
            int[] array = translate.getIndexesForServer(x, y);
            getCONTEXT().put("attack x", array[0]);
            getCONTEXT().put("attack y", array[1]);
        }

        public String getMoveCommand() {
            return (String) getCONTEXT().get("move command");
        }

        public void setMoveCommand(String command) {
            getCONTEXT().put("move command", command);
        }

    }

    public static class Response extends CommunicationBetweenTheServerAndTheViewer.Response {

        private String namePlayer;
        private String nameOpponent;


        public Response(TranslateForServerOrView translate) {
            super(translate);
            defaultResponse();
        }

        private void defaultResponse() {
            setArrayShip(new int[]{0, 0, 0, 0}, new int[]{0, 1, 2, 3});
            setAllShip(1);
            setShip(4);
            setName("name");
            setShipsNumbers(new int[4]);
            setPLAYERS_IS_READY(false);
            setTHERE_IS_A_WINNER(false);
            setOnlyPlayer(false);
            setPlayerMap(new HashMap<>());
            setOpponentMap(new HashMap<>());
        }

        public void setCursor(int x, int y) {
            int[] array = translate.getPixelCoordinatesForView(x, y);
            getCONTEXT().put("cursor x", array[0]);
            getCONTEXT().put("cursor y", array[1]);
        }

        public int getCursor(String byCoordinate) {
            return (int) (byCoordinate.equals("x") ? getCONTEXT().get("cursor x") : getCONTEXT().get("cursor y"));
        }

        public void setOnlyPlayer(boolean isOnly) {
            getCONTEXT().put("only player", isOnly);
        }

        public boolean isOnlyPlayer() {
            return (boolean) getCONTEXT().get("only player");
        }

        public Map<String, Object> CONTEXT = new HashMap<>();

        public Map<String, Object> getCONTEXT() {
            return CONTEXT;
        }

        public void setName(String name) {
            this.namePlayer = name;
        }

        public void setNameOpponent(String name) {
            this.nameOpponent = name;
        }

        public String getNameOpponent() {
            return nameOpponent;
        }

        public String getName() {
            return namePlayer;
        }

        public void setShip(int ship) {
            getCONTEXT().put("ship", ship);
        }

        public int getShip() {
            return (int) getCONTEXT().get("ship");
        }

        public void setShipsNumbers(int[] shipsNumbers) {
            getCONTEXT().put("ships numbers", shipsNumbers);
        }

        public int[] getShipsNumbers() {
            return (int[]) getCONTEXT().get("ships numbers");
        }

        public void setAllShip(int allShip) {
            getCONTEXT().put("all ships", allShip);
        }

        public int getAllShips() {
            return (int) getCONTEXT().get("all ships");
        }

        public void setPlayerMap(Map<Integer, Map<Integer, Integer>> playerMap) {
            getCONTEXT().put("player map", playerMap);
        }

        public Map<Integer, Map<Integer, Integer>> getPlayerMap() {
            return (Map<Integer, Map<Integer, Integer>>) getCONTEXT().get("player map");
        }

        public void setOpponentMap(Map<Integer, Map<Integer, Integer>> opponentMap) {
            getCONTEXT().put("opponent map", opponentMap);
        }

        public Map<Integer, Map<Integer, Integer>> getOpponentMap() {
            return (Map<Integer, Map<Integer, Integer>>) getCONTEXT().get("opponent map");
        }

        public void setArrayShip(int[] shipX, int[] shipY) {
            int[][] array = translate.getShipPixelCoordinatesForView(shipX, shipY);
            getCONTEXT().put("ship x", array[0]);
            getCONTEXT().put("ship y", array[1]);
        }

        public int[] getArrayShip(String coordinate) {
            return coordinate.equals("x") ? (int[]) getCONTEXT().get("ship x") : (int[]) getCONTEXT().get("ship y");
        }

    }
}
