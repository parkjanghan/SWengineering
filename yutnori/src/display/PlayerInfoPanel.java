package display;

import GameController.YutnoriSet;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoPanel extends JPanel {

    private JLabel turnLabel;

    public PlayerInfoPanel(YutnoriSet yutnoriSet) {
        setLayout(new GridLayout(3, 1));
        setBackground(Color.WHITE);

        turnLabel = new JLabel("현재 턴: 플레이어 1", SwingConstants.CENTER);
        turnLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        add(turnLabel);
    }

    public void updatePlayerTurn(int playerTurn) {
        turnLabel.setText("현재 턴: 플레이어 " + (playerTurn + 1));
        System.out.println("[PlayerInfoPanel] 🧭 턴 갱신됨: 플레이어 " + (playerTurn + 1));
    }
}
