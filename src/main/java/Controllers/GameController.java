package Controllers;

import Enums.Directions;
import Models.Battlefield;
import Models.Coordinates;
import Models.Robot;
import Services.RobotService;
import Views.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


@RestController
public class GameController {

    private List<Robot> robotList = new ArrayList<>();


    public static void main(String[] args) throws IOException, InterruptedException {
        String url = ("https://82rvkz5o22.execute-api.eu-central-1.amazonaws.com/prod/");
        String jsonInputString ="{\"name\": \"Bjarne\", \"health\": 5, \"movementRate\": 4, \"attackDamage\": 3, \"attackRange\": 3}";

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
    private static void sendPostRequest(String url, String jsonInputString) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
