package Services;

import Enums.Directions;
import Models.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class MoveService {

    private List<Move> moves = new ArrayList<>();
    private List<Robot> robot = new ArrayList<>();
    private Map map;

    public List<Move> getMovesByGameId(String gameId){
        List<Move> gameMoves = new ArrayList<>();
        for (Move move : moves){
            if (move.getGameId().equals(gameId)){
                gameMoves.add(move);
            }
        }
        return gameMoves;
    }

    public MoveService() {
        this.map = new Map(15, 10);
    }

    public Move makeMove(String gameId, String playerId, NewMove newMove) {
        Robot player = getRobotById(playerId);
        Robot target = getTargetByGameId(gameId);

        while (!player.isKnockedOut() && !target.isKnockedOut()) {
            int moveCount = 1;
            while (moveCount <= player.getMovementRange() && !player.isKnockedOut() && !target.isKnockedOut()) {
                Directions direction = newMove.getDirection();
                if (Map.validTurn(direction, player)) {
                    player.setX(player.getX() + direction.getX());
                    player.setY(player.getY() + direction.getY());
                    moveCount += 1;
                } else {
                    System.out.println("Zug ungültig.");
                }
                if (RobotService.inRange(player, target)) {
                    Robot.attack(player, target);
                }
            }
        }

        Move moveResult = new Move();
        moveResult.setPlayerId(playerId);
        moveResult.setGameId(gameId);
        moveResult.setMoveType(newMove.getMovementType());
        moveResult.setMoveDetails("Bewegung und Angriff erfolgreich durchgeführt!");
        moves.add(moveResult);
        return moveResult;
    }

    private Robot getRobotById(String playerId) {
        for (Robot robot : robot) {
            if (robot.getId().equals(playerId)) {
                return robot;
            }
        }
        return null;
    }

    private Robot getTargetByGameId(String gameId) {
        if (map.getId().equals(gameId)) {
            return map.getTarget();
        }
        return null;
    }
}


