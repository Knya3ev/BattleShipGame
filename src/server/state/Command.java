package server.state;

import java.util.Locale;

public enum Command {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    SPACE,
    ENTER;

    public String getSelfString(){
        return name().toLowerCase(Locale.ROOT);
    }
}
