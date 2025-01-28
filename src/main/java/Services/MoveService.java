package Services;

import Enums.Directions;
import Models.*;
import Views.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class MoveService {

    private List<Robot> robots = new ArrayList<>();
    private List <Map> maps = new ArrayList<>();
    private List <Move> moves = new ArrayList<>();

    public List<Move> getMovesByGameId(String gameId) {
        List<Move> gameMoves = new ArrayList<>();
        for (Move move : moves) {
            if (move.getGameId().equals(gameId)) {
                moves.add(move);
            }
        }
        return gameMoves;
    }
    public Move makeMove(String gameId, String playerId, NewMove newMove) {
        Robot player = getRobotById(playerId);
        Robot target = getTargetByGameId(gameId);

        while (!player.isKnockedOut() && !target.isKnockedOut()) {
            DisplayWinnerView.display(player, target);
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
                BattlefieldView.display(robots, maps);

                if (RobotService.inRange(player, target)) {
                    Robot.attack(player, target);
                    DisplayWinnerView.display(player, target);
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
        for (Robot robot : robots) {
            if (robot.getId().equals(playerId)) {
                return robot;
            }
        }
        return null;
    }

    private Robot getTargetByGameId(String gameId) {
        for (MapData map : maps) {
            if (map.getId().equals(gameId)) {
                return map.getTarget();
            }
        }
        return null;
    }
}
