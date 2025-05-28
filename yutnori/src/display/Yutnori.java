package display;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Yutnori extends Application {

    private Stage primaryStage;
    private Map<String, Scene> scenes = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // 초기 화면
        IntroPane intro = new IntroPane(this);
        scenes.put("intro", new Scene(intro, 1280, 720));

        // 설정 화면
        SettingPane setting = new SettingPane(this);
        scenes.put("setting", new Scene(setting, 1280, 720));

        // 초기화
        primaryStage.setTitle("Yutnori");
        primaryStage.setScene(scenes.get("intro"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void switchTo(String name) {
        if (scenes.containsKey(name)) {
            primaryStage.setScene(scenes.get(name));
        }
    }

    public void goToIntro() {
        switchTo("intro");
    }

    public void addGamePanel(GamePane gamePanel) {
        scenes.put("game", new Scene(gamePanel, 1280, 720));
    }
}
