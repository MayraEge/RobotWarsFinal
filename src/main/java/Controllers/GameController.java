package Controllers;

import Enums.Directions;
import Models.*;
import Services.*;
import Views.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class GameController {
    private List<Game> gameList = new ArrayList<>();
    private static final String api_url = ("https://82rvkz5o22.execute-api.eu-central-1.amazonaws.com/prod/");

    @PostMapping("/api/maps/map/{id}")
    public ResponseEntity<String> addMap(@RequestBody String json) {
        try {
            MapData[] mapDataArray = JsonParser.parseMapData(json);
            for (MapData mapData : mapDataArray) {
                Battlefield battlefield = new Battlefield(mapData.getMapSizeX(), mapData.getMapSize() / mapData.getMapSizeX());
                for (MapItem item : mapData.getMapItems()) {
                    int x = item.getIndex() % mapData.getMapSizeX();
                    int y = item.getIndex() / mapData.getMapSizeX();
                    battlefield.getMap()[x][y] = item.getType().equals("ROBOT") ? 1 : 2;
                }
                Game game = new Game(mapData.getId(), "GameID", battlefield, 2);
                gameList.add(game);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Map erfolgreich hinzugefügt!!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ungültige JSON-Daten.");
        }
    }

    @PostMapping("/api/games/game")
    public ResponseEntity<String> addGame(@RequestBody Game game) {
        if (game != null) {
            gameList.add(game);
            String jsonInputString = String.format("{\"name\": \"%s\", \"players\": %d}",
                    game.getName(), game.getPlayers());
            String url = api_url + "api/games/game";

            sendPostRequest(url, jsonInputString);
            return ResponseEntity.status(HttpStatus.CREATED).body("Spiel erfolgreich erstellt!!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ungültiges Spiel wird nicht erstellt.");
        }
    }

    @PostMapping("/api/games/{gameId}/join")
    public ResponseEntity<String> joinGame(@PathVariable String gameId, @RequestParam String playerName) {
        Optional<Game> gameOptional = gameList.stream().filter(game -> game.getId().equals(gameId)).findFirst();
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            game.addPlayerName(playerName);
            return ResponseEntity.ok("Jawoll, Spieler erfolgreich hinzugefügt!!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Spiel wurde nicht gefunden.");
        }
    }

    private void sendPostRequest(String url, String jsonInputString) {
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

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Response: " + response.toString());
                }
            } else {
                System.out.println("POST request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred during the POST request.");
        }
    }
}