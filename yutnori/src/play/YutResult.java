package play;

public enum YutResult {
    BACK_DO(0,"백도"),
    DO(1, "도"),
    GAE(2, "개"),
    GEOL(3, "걸"),
    YUT(4, "윷"),
    MO(5, "모");


    private final int moveCount;
    private final String name;

    YutResult(int moveCount, String name) {
        this.moveCount = moveCount;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}