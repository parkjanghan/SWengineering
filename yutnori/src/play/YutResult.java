package play;

public enum YutResult {
    DO(1, "도"),
    GAE(2, "개"),
    GEOL(3, "걸"),
    YUT(4, "윷"),
    MO(5, "모"),
    BACK_DO(-1, "백도");

    private final int moveCount;
    private final String name;

    YutResult(int moveCount, String name) {
        this.moveCount = moveCount;
        this.name = name;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public String getName() {
        return name;
    }
}