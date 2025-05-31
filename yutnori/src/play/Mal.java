package play;

import java.util.ArrayList;

public class Mal {
    private int team;
    private int malNumber;
    private int position;
    private boolean isFinished;
    private ArrayList<Mal> stackedMal;
    private Player owner;
    private boolean life;

    public Mal(int team, int malNumber, Player owner) {
        this.team = team;
        this.malNumber = malNumber;
        this.owner = owner;
        this.position = owner.getTeam() * (-1);
        this.isFinished = false;
        this.life = true;
        this.stackedMal = new ArrayList<>();
    }

    public int getTeam() {
        return team;
    }

    public int getMalNumber() {
        return malNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;

    }

    public void stackMal(Mal otherMal) {
        stackedMal.add(otherMal);
    }

    public void clearStackedMal() {
        stackedMal.clear();
    }

}
