package display;

import GameModel.YutnoriSet;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class IntroPane extends Pane {

    private final ImageView backgroundView;

    public IntroPane(Yutnori parentFrame) {
        setPrefSize(1280, 720);

        // 배경 이미지 로드 및 설정
        Image background = new Image(getClass().getResourceAsStream("/assets/images/IntroBackground.png"));
        backgroundView = new ImageView(background);
        backgroundView.setFitWidth(1280);
        backgroundView.setFitHeight(720);
        getChildren().add(backgroundView);

        // Start 버튼
        Button startBtn = new Button("Start");
        startBtn.setLayoutX(220);
        startBtn.setLayoutY(300);
        startBtn.setPrefSize(200, 100);
        getChildren().add(startBtn);

        // Setting 버튼
        Button settingBtn = new Button("Setting");
        settingBtn.setLayoutX(860);
        settingBtn.setLayoutY(300);
        settingBtn.setPrefSize(200, 100);
        getChildren().add(settingBtn);

        // Start 버튼 이벤트
        startBtn.setOnAction(e -> {
            int boardType = GameSettings.getBoardShape();

            if (boardType < 4 || boardType > 6) {
                boardType = 4;
            }

            YutnoriSet yutnoriSet = new YutnoriSet(boardType);
            GamePane gamePanel = new GamePane(yutnoriSet);
            parentFrame.addGamePanel(gamePanel);
            parentFrame.switchTo("game");
        });

        // Setting 버튼 이벤트
        settingBtn.setOnAction(e -> {
            parentFrame.switchTo("setting");
        });
    }
}
