package display;

import GameModel.YutnoriSet;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IntroPanel extends StackPane {

    private final YutnoriSet yutnoriSet;

    public IntroPanel(Stage stage, YutnoriSet yutnoriSet) {
        this.yutnoriSet = yutnoriSet;

        // 배경 이미지 설정
        Image bgImage = new Image(getClass().getResource("/assets/images/introBackground.png").toExternalForm());
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(800);
        bgView.setFitHeight(600);
        bgView.setPreserveRatio(false);

        // 버튼 생성
        Button startButton = new Button("게임 시작");
        Button settingButton = new Button("설정");

        // 버튼 이벤트
        startButton.setOnAction(e -> {
            GamePanel gamePanel = new GamePanel(stage, yutnoriSet);
            Scene gameScene = new Scene(gamePanel, 800, 600);
            stage.setScene(gameScene);
        });

        settingButton.setOnAction(e -> {
            SettingPanel settingPanel = new SettingPanel(stage, yutnoriSet);
            Scene settingScene = new Scene(settingPanel, 800, 600);
            stage.setScene(settingScene);
        });

        VBox buttonBox = new VBox(15, startButton, settingButton);
        buttonBox.setAlignment(Pos.CENTER);

        getChildren().addAll(bgView, buttonBox);
    }
}
