package display;

import GameModel.YutnoriSet;
import assets.*;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.*;
import java.util.List;

public class BoardPane extends Pane{

    private final YutnoriSet yutnoriSet;
    private BoardGraph boardGraph;
    private final Map<Integer, NodeButton> nodeButtons = new HashMap<>();
    private final List<MalButton> malButtons = new ArrayList<>();
    private final Map<Integer, Point2D> malPositions = new HashMap<>();
    private final Map<String, Label> nodeCountLabels = new HashMap<>();

    private int selectedPlayerId = -1;
    private int selectedMalId = -1;

    private final Map<Integer, Color> playerColors = Map.of(
            0, Color.RED, 1, Color.BLUE, 2, Color.GREEN, 3, Color.MAGENTA
    );

    public BoardPane(YutnoriSet yutnoriSet) {
        this.yutnoriSet = yutnoriSet;

        switch (GameSettings.getBoardShape()) {
            case 4 -> boardGraph = new BoardGraph4();
            case 5 -> boardGraph = new BoardGraph5();
            case 6 -> boardGraph = new BoardGraph6();
            default -> boardGraph = new BoardGraph4();
        }

        setPrefSize(1000, 720);
        setStyle("-fx-background-color: #f0f0f0;");

        // 노드 버튼 생성
        for (Map.Entry<Integer, MyPoint> entry : boardGraph.getNodePositions().entrySet()) {
            int nodeId = entry.getKey();
            MyPoint raw = entry.getValue();
            Point2D pos = new Point2D(raw.getX(), raw.getY());

            NodeButton btn = new NodeButton(nodeId, pos);
            btn.setDisable(true);
            btn.setCursor(Cursor.HAND);

            nodeButtons.put(nodeId, btn);
            getChildren().add(btn);
        }

        // 옵저버 등록
        //yutnoriSet.addObserver(this);
    }

    // 이후 메서드들은 순차적으로 변환해서 이어서 제공할게
}
