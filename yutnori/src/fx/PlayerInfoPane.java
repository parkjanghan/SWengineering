package fx;

import GameModel.YutnoriSet;

import javafx.geometry.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PlayerInfoPane extends VBox {
    // boardPanel.yutnoriSet 이용

    private Label turnLabel;
    ArrayList<Label> scoreLabels;
    private YutnoriSet yutnoriSet;

    public PlayerInfoPane(YutnoriSet yutnoriSet) {
        this.yutnoriSet = yutnoriSet;

        // VBox 설정
        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: white;");

        // 턴 레이블
        turnLabel = new Label("현재 턴: 플레이어 1");
        turnLabel.setFont(Font.font("맑은 고딕", FontWeight.BOLD, 16));
        turnLabel.setAlignment(Pos.CENTER);
        turnLabel.setMaxWidth(Double.MAX_VALUE);
        getChildren().add(turnLabel);

        // 점수 레이블들
        scoreLabels = new ArrayList<>();
        for (int i = 0; i < yutnoriSet.getPlayers().size(); i++) {
            Label scoreLabel = new Label("플레이어 " + (i + 1) + " 점수: 0");
            scoreLabel.setFont(Font.font("맑은 고딕", 14));
            scoreLabel.setAlignment(Pos.CENTER);
            scoreLabel.setMaxWidth(Double.MAX_VALUE);
            scoreLabels.add(scoreLabel);
            getChildren().add(scoreLabel);
        }
    }

    public void updatePlayerTurn(int playerTurn) {
        turnLabel.setText("현재 턴: 플레이어 " + (playerTurn + 1));
        System.out.println("[PlayerInfoPanel] 🧭 턴 갱신됨: 플레이어 " + (playerTurn + 1));
    }

    public void showPlayerScores() {
        for (int i = 0; i < yutnoriSet.getPlayers().size(); i++) {
            int score = yutnoriSet.getPlayers().get(i).getScore();

            // JavaFX에서 HTML 스타일링 대신 TextFlow 사용
            Text playerText = new Text("플레이어 " + (i + 1));
            playerText.setFont(Font.font("맑은 고딕", FontWeight.BOLD, 14));

            Text scoreText = new Text(" 점수: " + score);
            scoreText.setFont(Font.font("맑은 고딕", 14));
            scoreText.setFill(Color.BLUE);

            // 또는 간단하게 스타일 적용된 Label 사용
            scoreLabels.get(i).setText("플레이어 " + (i + 1) + " 점수: " + score);
            scoreLabels.get(i).setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

            // 점수 부분만 파란색으로 하려면 다음과 같이 할 수 있습니다:
            // scoreLabels.get(i).setStyle("-fx-text-fill: blue;");
        }
        System.out.println("[PlayerInfoPanel] 🏆 점수 갱신됨");
    }
}