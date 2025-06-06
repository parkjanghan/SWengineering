package fx;

import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;

import assets.*;

public class NodeButton extends Button {

    private final int nodeId;
    private boolean isHighlighted = false;
    private final Circle circle;
    private final int size = 30;

    public NodeButton(int nodeId, Point pos) {
        this.nodeId = nodeId;

        // 원형 노드 생성
        circle = new Circle(size / 2);
        circle.setStroke(Color.GRAY);
        circle.setStrokeWidth(2);
        circle.setFill(Color.rgb(255, 255, 255, 1.0));

        setLayoutX(pos.getX() - size / 2);
        setLayoutY(pos.getY() - size / 2);
        setPrefSize(size, size);

        getChildren().add(circle);

        this.setOpacity(1.0);
        this.setBackground(new Background(
                new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)
        ));

        setPickOnBounds(false); // 원형 클릭만 인식
        setCursor(javafx.scene.Cursor.HAND);
    }

    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;

        if (highlighted) {
            this.setBackground(new Background(
                    new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)
            ));
        } else {
            this.setBackground(new Background(
                    new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)
            ));
        }
    }

    public int getNodeId() {
        return nodeId;
    }

    public boolean isInside(double x, double y) {
        double r = size / 2.0;
        double dx = x - r;
        double dy = y - r;
        return dx * dx + dy * dy <= r * r;
    }

    // 선택적으로 클릭 이벤트 등록
    public void setOnNodeClick(javafx.event.EventHandler<? super MouseEvent> handler) {
        this.setOnMouseClicked(handler);
    }
}
