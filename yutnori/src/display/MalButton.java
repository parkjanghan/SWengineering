package display;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.StackPane;

public class MalButton extends StackPane {

    private final int playerId;
    private final int malId;
    private int nodeId;
    private boolean moved;
    private final Button button;

    public MalButton(int playerId, int malId, Color color) {
        this.playerId = playerId;
        this.malId = malId;
        this.moved = false;

        // 내부 버튼 생성
        button = new Button();
        button.setPrefSize(20, 20);
        button.setStyle("-fx-background-color: " + toRgbString(color) + "; -fx-background-radius: 10;");

        getChildren().add(button); // StackPane 위에 버튼 얹음
        setPrefSize(20, 20);
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getMalId() {
        return malId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Button getButton() {
        return button;
    }

    private String toRgbString(Color color) {
        return String.format("rgb(%d,%d,%d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
