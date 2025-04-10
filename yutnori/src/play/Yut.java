package play;

public class Yut {
    private YutResult result;
    private int moveCount;
    private String name;

    public Yut(YutResult result, String name) {
        this.result = result;
        this.name = name;
        this.moveCount = result.getMoveCount();
    }

    public YutResult getResult() {
        return result;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public String getName() {
        return name;
    }
}
