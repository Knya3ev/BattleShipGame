package server.state;

import java.util.Locale;

public enum StateGameBody {
    ATTACK,
    RESTART,
    MOVE,
    PUSH_SPACE_BAR;

    public String getName(){
        return name().toLowerCase(Locale.ROOT).replace("_"," ");
    }
}
