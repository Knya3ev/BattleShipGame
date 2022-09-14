package server.state;

import java.util.Locale;

public enum StateGameHead {
    START(true),
    CREATE_PLAYER(false),
    BATTLE(false),
    GAME_OVER(false);

    private boolean state;
    StateGameHead(boolean state) {
        this.state=state;
    }
    public void setState(boolean state){
        this.state = state;
    }
    public boolean isState(){
        return this.state;
    }
    public String getStateName(){
        return name().toLowerCase(Locale.ROOT).replace("_"," ");
    }

}
