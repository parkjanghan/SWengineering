package display;

import GameController.YutnoriSet;

import javax.swing.*;
import java.awt.*;

public class IntroPanel extends JPanel {

    private Image background;

    public IntroPanel(Yutnori parentFrame) {
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));

        // 배경 이미지 로드
        background = new ImageIcon(getClass().getResource("/assets/Images/introBackground.png")).getImage();

        // Start 버튼
        JButton startBtn = new JButton("Start");
        startBtn.setBounds(220, 300, 200, 100);
        add(startBtn);

        // Setting 버튼
        JButton settingBtn = new JButton("Setting");
        settingBtn.setBounds(860, 300, 200, 100);
        add(settingBtn);

        // 이벤트
        
        //startBtn.addActionListener(e -> parentFrame.switchTo("game"));
        startBtn.addActionListener(e -> {
            int boardType = GameSettings.getBoardShape();  //설정값 불러오기

            if (boardType < 4 || boardType > 6) {
                boardType = 4; // 기본값 보정
            }

            YutnoriSet yutnoriSet = new YutnoriSet(boardType); // 예외 방지

            GamePanel gamePanel = new GamePanel(yutnoriSet);
            parentFrame.addGamePanel(gamePanel);
            parentFrame.switchTo("game");
        });


        settingBtn.addActionListener(e -> parentFrame.switchTo("setting"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
