package server.setting;

public enum TableSetting {
    BEGINNING_OF_THE_COLUMN(0),
    END_OF_THE_COLUMN(10),
    STEP_OF_THE_COLUMN(1);
    private final int value;
    TableSetting(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
