package Models;
import Enums.Directions;

public class NewMove {
    private Directions direction;
    private String movementType;

    // Getter und Setter
    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }
}
