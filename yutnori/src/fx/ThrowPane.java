package fx;

import GameModel.YutnoriSet;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import play.YutResult;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class ThrowPane extends VBox implements PropertyChangeListener {

    private final YutnoriSet yutnoriSet;
    private final Label resultLabel;
    private final FlowPane resultPanel;
    private final List<Button> allButtons = new ArrayList<>();

    public ThrowPane(YutnoriSet yutnoriSet) {
        this.yutnoriSet = yutnoriSet;
        setSpacing(10);
        setPadding(new Insets(10));

        // --- 남은 결과 라벨 ---
        resultLabel = new Label("남은 결과: 없음");
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        getChildren().add(resultLabel);

        // --- 랜덤 윷 던지기 버튼 ---
        Button randomBtn = new Button("🎲 랜덤 윷 던지기");
        allButtons.add(randomBtn);
        randomBtn.setOnAction(e -> {
            int currentPlayer = yutnoriSet.getPlayerTurn();
            if (!yutnoriSet.isCurrentPlayerCanThrow()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("지금은 플레이어 " + (currentPlayer + 1) + "의 턴입니다.");
                alert.showAndWait();
                return;
            }
            System.out.println("[ThrowPanel] 🎲 윷 던지기 버튼 클릭됨");
            yutnoriSet.rollYut();
        });

        HBox topPanel = new HBox(randomBtn);
        topPanel.setSpacing(10);
        getChildren().add(topPanel);

        // --- 수동 윷 선택 버튼들 ---
        GridPane yutButtons = new GridPane();
        yutButtons.setHgap(5);
        yutButtons.setVgap(5);

        String[] names = {"빽", "도", "개", "걸", "윷", "모"};
        YutResult[] results = {
                YutResult.BACK_DO, YutResult.DO, YutResult.GAE,
                YutResult.GEOL, YutResult.YUT, YutResult.MO
        };

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            YutResult result = results[i];

            Button btn = new Button(name);
            btn.setStyle("-fx-font-size: 10px;");
            allButtons.add(btn);
            btn.setOnAction(e -> {
                System.out.println("[ThrowPanel] 🖐 수동 윷 결과 선택: " + result.getName());
                yutnoriSet.rollYutforTest(result);
            });

            yutButtons.add(btn, i, 0);
        }

        getChildren().add(yutButtons);

        // --- 결과 버튼 표시 영역 ---
        resultPanel = new FlowPane();
        resultPanel.setHgap(10);
        resultPanel.setVgap(10);
        getChildren().add(resultPanel);

        // 옵저버 등록
        yutnoriSet.addObserver(this);
    }

    // 옵저버 콜백
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();
        if ("사용자의 결과 추가됨".equals(property) || "사용자의 결과 삭제됨".equals(property)) {
            updateResultDisplay();
        } else if ("말 이동됨".equals(property)) {
            for (javafx.scene.Node node : resultPanel.getChildren()) {
                if (node instanceof Button) {
                    node.setDisable(false);
                }
            }
        }
    }

    public void showYutResult(YutResult result) {
        System.out.println("[ThrowPanel] 📢 showYutResult() 호출됨");
        // 결과 표시 화면 업데이트
        updateResultDisplay();
    }

    public void enableAllButtons(boolean enable) {
        for (Button b : allButtons) {
            b.setDisable(!enable);
        }
    }

    public void updateResultDisplay() {
        // 결과 패널 초기화
        resultPanel.getChildren().clear();
        System.out.println("[ThrowPanel] 📢 updateResultDisplay() - 현재 playerResults: " + yutnoriSet.getPlayerResults());
        List<YutResult> results = yutnoriSet.getPlayerResults();

        if (results == null || results.isEmpty()) {
            Label noResultLabel = new Label("남은 결과: 없음");
            noResultLabel.setStyle("-fx-font-size: 14px;");
            resultPanel.getChildren().add(noResultLabel);
        } else {
            Label titleLabel = new Label("사용할 결과: ");
            titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            resultPanel.getChildren().add(titleLabel);

            for (YutResult result : results) {
                Button resultBtn = new Button(result.getName());
                resultBtn.setStyle("-fx-font-size: 12px;");

                resultBtn.setOnAction(e -> {
                    // 다른 버튼들 비활성화
                    for (javafx.scene.Node node : resultPanel.getChildren()) {
                        if (node instanceof Button) {
                            node.setDisable(true);
                        }
                    }

                    // 결과 사용 처리
                    yutnoriSet.setYutResult_to_use(result);

                    // 이 버튼 제거
                    resultPanel.getChildren().remove(resultBtn);
                });

                resultPanel.getChildren().add(resultBtn);
            }
        }
    }

}
