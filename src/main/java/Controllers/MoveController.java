package Controllers;
import Models.Move;
import Models.NewMove;
import Services.MoveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoveController {

    @Autowired
    private MoveService moveService;

    @GetMapping("/game/{gameId}")
    public List<Move> getMovesByGameId(@PathVariable String gameId) {
        return moveService.getMovesByGameId(gameId);
    }

    @GetMapping("/api/games/game/{id}/move")
    public List <Move> getAllMoves(@PathVariable String id){
        return moveService.getMovesByGameId(id);
    }

    @PostMapping("/api/games/game/{id}/move/player/{playerId}")
    public Move makeMove(@PathVariable String id, @PathVariable String playerId, @RequestBody NewMove newMove) {
        return moveService.makeMove(id, playerId, newMove);
    }
}
