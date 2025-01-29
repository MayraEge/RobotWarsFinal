package Services;

import Enums.Directions;
import Models.Robot;

public class RobotService {
    public static Enum<Directions> fromUserInput(int userChoice){
        return switch (userChoice) {
            case 8 -> Directions.NORTH;
            case 9 -> Directions.NORTH_EAST;
            case 6 -> Directions.EAST;
            case 3 -> Directions.SOUTH_EAST;
            case 2 -> Directions.SOUTH;
            case 1 -> Directions.SOUTH_WEST;
            case 4 -> Directions.WEST;
            case 7 -> Directions.NORTH_WEST;
            default -> Directions.NOMOVE;
        };
    }
    public static boolean inRange(Robot player, Robot target) {
        int dx = Math.abs(player.getX() - target.getX());
        int dy = Math.abs(player.getY() - target.getY());
        return dx <= player.getAttackRange() && dy <= player.getAttackRange();
    }
}