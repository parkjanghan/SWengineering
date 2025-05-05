package play;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int team;
    private int score;
    private ArrayList<Mal> malList;
    private boolean turn;

    public Player(int team) {
        this.team = team;
        this.score = 0;
        this.turn = false;
        this.malList = new ArrayList<>();
    }

    public int getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public ArrayList<Mal> getMalList() {
        return malList;
    }

    public void addMal(Mal mal) {
        malList.add(mal);
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
