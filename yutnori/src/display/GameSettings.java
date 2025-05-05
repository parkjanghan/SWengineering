package display;

public class GameSettings {
    private static int playerCount = 2;
    private static int malCount = 4;
    private static int boardShape = 4;

    public static void setPlayerCount(int count) {
        playerCount = count;
    }

    public static void setMalCount(int count) {
        malCount = count;
    }

    public static void setBoardShape(int shape) {
        boardShape = shape;
    }

    public static int getPlayerCount() {
        return playerCount;
    }

    public static int getMalCount() {
        return malCount;
    }

    public static int getBoardShape() {
        return boardShape;
    }
}
