package display;

import javax.swing.*;
import java.awt.*;

public class Yutnori extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel();

    public Yutnori() {
        setTitle("Yutnori");
        setSize(1280, 720); // Use constants from Main.java
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setLayout(cardLayout);

        IntroPanel intro = new IntroPanel(this);
        //GamePanel game = new GamePanel(this); // YutnoriSet은 null로 초기화
        SettingPanel setting = new SettingPanel(this);

        mainPanel.add(intro, "intro");
        //mainPanel.add(game, "game");
        mainPanel.add(setting, "setting");

        add(mainPanel);

        setVisible(true); // 반드시 마지막에 호출
    }

    public void switchTo(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public void goToIntro() {
        switchTo("intro");
    }

    public void addGamePanel(JPanel gamePanel) {
        mainPanel.add(gamePanel, "game");
    }

}
