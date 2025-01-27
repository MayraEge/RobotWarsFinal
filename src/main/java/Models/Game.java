package Models;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String id;
    private String name;
    private Battlefield battlefield;
    private int players;
    private List<String> playerNames;
    private String mapId;

    public Game(String id, String name, Battlefield battlefield, int players) {
        this.id = id;
        this.name = name;
        this.battlefield = battlefield;
        this.players = players;
        this.playerNames = new ArrayList<>();
    }
        public String getId(){
            return id;
        }
        public void setId(String id){
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getPlayers(){
        return players;
        }
        public void setPlayers(int players){
        this.players = players;
        }
        public List<String> getPlayerNames(){
        return playerNames;
        }
        public void addPlayerName(String playerNames){
        this.playerNames.add(playerNames);
        }
        public String getMapId() {
            return mapId;
        }
        public Battlefield getBattlefield() {
            return battlefield;
        }
        public void setBattlefield(Battlefield battlefield) {
            this.battlefield = battlefield;
        }
        public void setMapId(String mapId) {
            this.mapId = mapId;
        }
}
