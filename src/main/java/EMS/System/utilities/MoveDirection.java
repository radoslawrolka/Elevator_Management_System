package EMS.System.utilities;

public enum MoveDirection {
    UP,
    STOP,
    DOWN,
    IDLE;

    public int getValue() {
        return switch (this) {
            case UP -> 1;
            case DOWN -> -1;
            default -> 0;
        };
    }


}
