package display;


import java.util.Random;

public class YutLogic {

    private static final Random rand = new Random();

    // 1~5 (도~모) 랜덤으로 결과 반환 (랜덤 윷 던지기)
    public static int rollYut() {
        return rand.nextInt(5) + 1;
    }

    public static String getResultName(int value) {
        return switch (value) {
            case 1 -> "도";
            case 2 -> "개";
            case 3 -> "걸";
            case 4 -> "윷";
            case 5 -> "모";
            default -> "오류";
        };
    }

    public static int getValueFromName(String name) {
        return switch (name.trim()) {
            case "도" -> 1;
            case "개" -> 2;
            case "걸" -> 3;
            case "윷" -> 4;
            case "모" -> 5;
            default -> -1; // 잘못된 입력
        };
    }
}
