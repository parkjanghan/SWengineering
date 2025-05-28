package display;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;


public class SettingPane extends Pane {

    private ComboBox<String> playerSelect;
    private ComboBox<String> malSelect;
    private ComboBox<String> boardSelect;


    public SettingPane(Yutnori parentFrame) {
        setPrefSize(1280, 720);
        setStyle("-fx-background-color: white;");

        // ------------------ 플레이어 수 ------------------
        Label playerLabel = new Label("플레이어 수:");
        playerLabel.setLayoutX(300);
        playerLabel.setLayoutY(180);
        playerLabel.setPrefWidth(150);
        playerLabel.setPrefHeight(30);
        getChildren().add(playerLabel);

        playerSelect = new ComboBox<>();
        playerSelect.getItems().addAll("2명", "3명", "4명");
        playerSelect.getSelectionModel().selectFirst();
        playerSelect.setLayoutX(450);
        playerSelect.setLayoutY(180);
        playerSelect.setPrefWidth(200);
        playerSelect.setPrefHeight(30);
        getChildren().add(playerSelect);

        // ------------------ 말 개수 ------------------
        Label malLabel = new Label("말 개수:");
        malLabel.setLayoutX(300);
        malLabel.setLayoutY(230);
        malLabel.setPrefWidth(150);
        malLabel.setPrefHeight(30);
        getChildren().add(malLabel);

        malSelect = new ComboBox<>();
        malSelect.getItems().addAll("2개", "3개", "4개", "5개");
        malSelect.getSelectionModel().selectFirst();
        malSelect.setLayoutX(450);
        malSelect.setLayoutY(230);
        malSelect.setPrefWidth(200);
        malSelect.setPrefHeight(30);
        getChildren().add(malSelect);

        // ------------------ 보드 모양 ------------------
        Label boardLabel = new Label("보드 모양:");
        boardLabel.setLayoutX(300);
        boardLabel.setLayoutY(280);
        boardLabel.setPrefWidth(150);
        boardLabel.setPrefHeight(30);
        getChildren().add(boardLabel);

        boardSelect = new ComboBox<>();
        boardSelect.getItems().addAll("사각형", "오각형", "육각형");
        boardSelect.getSelectionModel().selectFirst();
        boardSelect.setLayoutX(450);
        boardSelect.setLayoutY(280);
        boardSelect.setPrefWidth(200);
        boardSelect.setPrefHeight(30);
        getChildren().add(boardSelect);

        // ------------------ 완료 버튼 ------------------
        Button applyBtn = new Button("설정 완료");
        applyBtn.setLayoutX(550);
        applyBtn.setLayoutY(360);
        applyBtn.setPrefWidth(150);
        applyBtn.setPrefHeight(50);
        getChildren().add(applyBtn);

        applyBtn.setOnAction(e -> {
            int playerCount = playerSelect.getSelectionModel().getSelectedIndex() + 2; // 2 ~ 4
            int malCount = malSelect.getSelectionModel().getSelectedIndex() + 2;
            int boardShape = boardSelect.getSelectionModel().getSelectedIndex() + 4; // 4: 사각형, 5: 오각형, 6: 육각형

            // 예: GameSettings 같은 클래스에 저장
            GameSettings.setPlayerCount(playerCount);
            GameSettings.setMalCount(malCount);
            GameSettings.setBoardShape(boardShape);

            System.out.println("설정 완료:");
            System.out.println("- 플레이어 수: " + playerCount);
            System.out.println("- 말 개수: " + malCount);
            System.out.println("- 보드 모양: " + boardShape);

            parentFrame.switchTo("intro");
        });


        // ------------------ 뒤로가기 버튼 ------------------
        Button backBtn = new Button("뒤로가기");
        backBtn.setLayoutX(50);
        backBtn.setLayoutY(50);
        backBtn.setPrefWidth(100);
        backBtn.setPrefHeight(30);
        getChildren().add(backBtn);

        backBtn.setOnAction(e -> {
            parentFrame.switchTo("intro");
        });


    }
}
