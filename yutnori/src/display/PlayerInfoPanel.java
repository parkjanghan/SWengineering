package display;

import GameController.YutnoriSet;
import play.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerInfoPanel extends JPanel {
//boardPanel.yutnoriSet이용

    private JLabel turnLabel;
    ArrayList<JLabel> scoreLabels ;
    private YutnoriSet yutnoriSet;

    public PlayerInfoPanel(YutnoriSet yutnoriSet) {
        this.yutnoriSet = yutnoriSet;
        setLayout(new GridLayout(3, 1));

        setBackground(Color.WHITE);

        turnLabel = new JLabel("현재 턴: 플레이어 1", SwingConstants.CENTER);
        turnLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        add(turnLabel);
        scoreLabels = new ArrayList<>();
        for (int i = 0; i < yutnoriSet.getPlayers().size(); i++) {
            JLabel scoreLabel = new JLabel("플레이어 " + (i + 1) + " 점수: 0", SwingConstants.CENTER);
            scoreLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            scoreLabels.add(scoreLabel);
            add(scoreLabel); // 패널에 추가
        }
    }

    public void updatePlayerTurn(int playerTurn) {
        turnLabel.setText("현재 턴: 플레이어 " + (playerTurn + 1));
        System.out.println("[PlayerInfoPanel] 🧭 턴 갱신됨: 플레이어 " + (playerTurn + 1));

    }

    public void showPlayerScores() {

        for (int i = 0; i < yutnoriSet.getPlayers().size(); i++) {
            int score = yutnoriSet.getPlayers().get(i).getScore();
            scoreLabels.get(i).setText("플레이어 " + (i + 1) + " 점수: " + score);
        }
        System.out.println("[PlayerInfoPanel] 🏆 점수 갱신됨");
        for(Player players : yutnoriSet.getPlayers()){
            System.out.println("플레이어 " + players.getTeam() + " 점수: " + players.getScore());
        }
    }


}
