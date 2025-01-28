package Models;

public class Move {
    private String id;
    private String playerId;
    private String gameId;
    private String moveType;
    private String moveDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    public String getMoveDetails() {
        return moveDetails;
    }

    public void setMoveDetails(String moveDetails) {
        this.moveDetails = moveDetails;
    }
}
