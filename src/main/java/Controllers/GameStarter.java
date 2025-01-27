package Controllers;

import Models.Battlefield;
import Models.Coordinates;
import Models.Robot;
import Enums.Directions;
import Services.*;
import Views.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameStarter {
    public static void main(String[] args) throws IOException, InterruptedException {
        IntroScreenView.display();
        Battlefield battlefield = new Battlefield(15, 10);
        String robotName = AskRobotNameView.display();
        Coordinates defaultCoordinates = new Coordinates(0, 0, 15, 10);
        Robot player = new Robot("1", robotName, 1, 1, 1, 1, false);
        Robot target = new Robot("2", "[O]", 1, 1, 1, 1, false);

        AskSkillPointsView.setStats(player);
        AskSkillPointsView.display(player);

        List<Robot> robots = new ArrayList<>();
        robots.add(player);
        robots.add(target);
        System.out.println("Sie haben folgenden Roboter ausgewählt: " + player.getName());

        BattlefieldView.display(robots, battlefield);
        while (!player.isKnockedOut() && !target.isKnockedOut()) {
            DisplayWinnerView.display(player, target);
            int move = 1;
            while (move <= player.getMovementRange() && !player.isKnockedOut() && !target.isKnockedOut()) {
                Directions direction = MoveRobotView.turn();
                if (Battlefield.validTurn(direction, player)) {
                    player.setX(player.getX() + direction.getX());
                    player.setY(player.getY() + direction.getY());
                    move += 1;
                } else {
                    System.out.println("Zug ungültig.");
                }
                BattlefieldView.display(robots, battlefield);

                if (RobotService.inRange(player, target)) {
                    Robot.attack(player, target);
                    DisplayWinnerView.display(player, target);
                }
            }
        }
    }
}
