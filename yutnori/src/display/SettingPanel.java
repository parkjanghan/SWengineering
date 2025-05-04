package display;

import javax.swing.*;
import java.awt.*;

public class SettingPanel extends JPanel {

    private JComboBox<String> playerSelect;
    private JComboBox<String> malSelect;
    private JComboBox<String> boardSelect;

    public SettingPanel(Yutnori parentFrame) {
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));
        setBackground(Color.WHITE);

        // ------------------ 플레이어 수 ------------------
        JLabel playerLabel = new JLabel("플레이어 수:");
        playerLabel.setBounds(300, 180, 150, 30);
        add(playerLabel);

        playerSelect = new JComboBox<>(new String[] {"2명", "3명", "4명"});
        playerSelect.setBounds(450, 180, 200, 30);
        add(playerSelect);

        // ------------------ 말 개수 ------------------
        JLabel malLabel = new JLabel("말 개수:");
        malLabel.setBounds(300, 230, 150, 30);
        add(malLabel);

        malSelect = new JComboBox<>(new String[] {"2개", "3개", "4개"});
        malSelect.setBounds(450, 230, 200, 30);
        add(malSelect);

        // ------------------ 보드 모양 ------------------
        JLabel boardLabel = new JLabel("보드 모양:");
        boardLabel.setBounds(300, 280, 150, 30);
        add(boardLabel);

        boardSelect = new JComboBox<>(new String[] {"사각형", "오각형", "육각형"});
        boardSelect.setBounds(450, 280, 200, 30);
        add(boardSelect);

        // ------------------ 완료 버튼 ------------------
        JButton applyBtn = new JButton("설정 완료");
        applyBtn.setBounds(550, 360, 150, 50);
        add(applyBtn);

        applyBtn.addActionListener(e -> {
            int playerCount = playerSelect.getSelectedIndex() + 2; // 2 ~ 4
            int malCount = malSelect.getSelectedIndex() + 2;
            int boardShape = boardSelect.getSelectedIndex() + 4; // "사각형" 등

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
        JButton backBtn = new JButton("뒤로가기");
        backBtn.setBounds(50, 50, 100, 30);
        add(backBtn);

        backBtn.addActionListener(e -> {
            parentFrame.switchTo("intro");
        });
    }
}
