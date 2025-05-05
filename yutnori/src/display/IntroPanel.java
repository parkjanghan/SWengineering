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
        background = new ImageIcon(getClass().getResource("/assets/images/introBackground.png")).getImage();

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
            // 게임 세팅값으로 YutnoriSet 초기화
            YutnoriSet yutnoriSet = new YutnoriSet(GameSettings.getBoardShape()); // 보드 타입 4
            yutnoriSet.setPlayer(GameSettings.getPlayerCount(), GameSettings.getMalCount());
        
            // GamePanel에 YutnoriSet 주입
            GamePanel gamePanel = new GamePanel(yutnoriSet);
            parentFrame.setContentPane(gamePanel);
            parentFrame.revalidate();
        });

        settingBtn.addActionListener(e -> parentFrame.switchTo("setting"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
