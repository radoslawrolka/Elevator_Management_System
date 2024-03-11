package EMS.System.utilities;

public enum ElevatorStatus {
    RUNNING,
    OVERLOADED,
    OUT_OF_SERVICE;

    public ElevatorStatus opposite() {
        return switch (this) {
            case RUNNING -> OUT_OF_SERVICE;
            case OUT_OF_SERVICE, OVERLOADED -> RUNNING;
        };
    }
}