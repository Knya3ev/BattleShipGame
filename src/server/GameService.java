package server;

import communication.Chat;
import server.state.StateGameBody;
import server.state.StateGameHead;

import java.util.ArrayList;


public class GameService {
    private final PlayerService playerService = new PlayerService(
            new Player("Player1", 1),
            new Player("Player2", 0));
    private Chat.Response response;
    private final ActionHandlers handlers = new ActionHandlers(playerService);


    public GameService(Chat.Response response){
        this.response = response;
    }

    public Chat.Response listener(Chat.Request request) {
        switch (request.getHEAD()) {
            case "start": start();
            case "create player": playerFleetDeployment(request);
            case "battle": battle(request);
            case "game over" : gameOver(request);
        }
        request.setBODY("empty");
        return response;
    }
    private void start(){
        StateGameHead.START.setState(false);
        StateGameHead.CREATE_PLAYER.setState(true);
    }
    private void playerFleetDeployment(Chat.Request request) {

        if(playerService.getShipCurrentPLayer() == 0) {
            playerService.getThePlayerBeingCreated().setThePlayerISReadyToFight(true);
            response.setArrayShip(new int[]{0,0,0,0},new int[]{0,1,2,3});
        }

        if(StateGameBody.MOVE.getName().equals(request.getBODY())){

            ArrayList<int[]> ships =handlers.moveShips(
                    request.getMoveCommand(),
                    request.getArrayShip("x"),
                    request.getArrayShip("y"));

            if(!ships.isEmpty()) response.setArrayShip(ships.get(0),ships.get(1));

        }

        if(playerService.playersIsReady()){
            StateGameHead.CREATE_PLAYER.setState(false);
            StateGameHead.BATTLE.setState(true);
        }

        Player player = playerService.getThePlayerBeingCreated();
        response.setPLAYERS_IS_READY(playerService.playersIsReady());
        response.setName(player.getName());
        response.setShip(playerService.getShipCurrentPLayer());
        response.setShipsNumbers(player.getNumberShips());
        response.setPlayerMap(player.getMapToPlayer());
    }

    private void battle(Chat.Request request) {
        if(playerService.playersIsReady()) {
            if (StateGameBody.ATTACK.getName().equals(request.getBODY()) && playerService.getPlayer().isOnlyPlayer()) {
                playerService.attack(
                        request.getAttack("x"),
                        request.getAttack("y"));

                handlers.checkDeadShip(request.getAttack("x"), request.getAttack("y"));
            }
            if(StateGameBody.MOVE.getName().equals(request.getBODY())){
                int[] array =handlers.moveCursor(
                        request.getMoveCommand(),
                        request.getCursor("x"),
                        request.getCursor("y"));
                response.setCursor(array[0],array[1]);
            }

            if(StateGameBody.PUSH_SPACE_BAR.getName().equals(request.getBODY())){
                playerService.getPlayer().setOnlyPlayer(true);
            }

            if (playerService.thereISaWinner()){
                StateGameHead.BATTLE.setState(false);
                StateGameHead.GAME_OVER.setState(true);
            }

            response.setTHERE_IS_A_WINNER(playerService.thereISaWinner());
            response.setOnlyPlayer(playerService.getPlayer().isOnlyPlayer());
            response.setName(playerService.getPlayer().getName());
            response.setNameOpponent(playerService.getOpponent().getName());
            response.setAllShip(playerService.getOpponent().getNumberOFAllShips());
            response.setPlayerMap(playerService.getPlayer().getMapToPlayer());
            response.setOpponentMap(playerService.getPlayer().getMapToOpponent());
        }
    }

    private void gameOver(Chat.Request request) {
        if (StateGameBody.RESTART.getName().equals(request.getBODY())) restartGame();
        if(response.isTHERE_IS_A_WINNER()) {
            response.setName(playerService.getWinner().getName());
            response.setPlayerMap(playerService.getWinner().getMapToPlayer());
        }
    }

    private void restartGame() {
        playerService.restartPLayers();
        response.setTHERE_IS_A_WINNER(false);
    }

}



