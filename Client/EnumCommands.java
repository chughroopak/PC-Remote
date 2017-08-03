package Client;

/**
 * Used to represent commands which can be sent by the server
 */
public enum EnumCommands {
    PRESS_MOUSE(1),
    RELEASE_MOUSE(2),
    PRESS_KEY(3),
    RELEASE_KEY(4),
    MOVE_MOUSE(5);

    private int abbrev;
    
    /*
    *initialise abbrev variable
    */
    EnumCommands(int abbrev) {
        this.abbrev = abbrev;
    }

    /*
    *function returns abbrev value
    */
    public int getAbbrev() {
        return abbrev;
    }
}
