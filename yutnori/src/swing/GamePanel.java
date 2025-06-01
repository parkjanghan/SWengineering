package swing;

import GameModel.YutnoriSet;
import GameModel.GameSettings;
import play.YutResult;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private final YutnoriSet yutnoriSet;
    private BoardPanel boardPanel;
    private ThrowPanel throwPanel;
    private PlayerInfoPanel infoPanel;

    public GamePanel(YutnoriSet yutnoriSet) {
        setLayout(new BorderLayout());
        this.yutnoriSet = yutnoriSet;
        this.yutnoriSet.startGame(GameSettings.getPlayerCount(), GameSettings.getMalCount()); // ✅ 2인용, 말 4개로 초기화 추가

        // 1. 각 서브 패널에 yutnoriSet 전달
        boardPanel = new BoardPanel(yutnoriSet);
        throwPanel = new ThrowPanel(yutnoriSet);
        infoPanel = new PlayerInfoPanel(yutnoriSet);

        // 2. 오른쪽 묶음 패널 생성 (수직 배치), 패널 추가
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout()); // 또는 BoxLayout으로도 가능
        rightPanel.add(infoPanel, BorderLayout.CENTER);
        rightPanel.add(throwPanel, BorderLayout.SOUTH);

        add(boardPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // 3. 옵저버 등록 (게임 로직에서 UI 상태 갱신받기)
        yutnoriSet.addObserver(evt -> {
            switch (evt.getPropertyName()) {
                case "모/윷이 나옴" -> {
                    throwPanel.showYutResult((YutResult) evt.getNewValue());
                    throwPanel.enableAllButtons(true);
                }
                case "윷 던지기 결과" -> {
                    throwPanel.showYutResult((YutResult) evt.getNewValue());
                    boardPanel.enableMalButtonsForPlayer(yutnoriSet.getPlayerTurn());
                    throwPanel.enableAllButtons(false);
                }
                case "사용할 결과 선택"-> {
                    throwPanel.enableAllButtons(true);
                    throwPanel.updateResultDisplay();
                    throwPanel.enableAllButtons(false);
                }
                case "말 이동됨" -> {
                    int[] data = (int[]) evt.getNewValue(); // [playerId, malId, destNodeId]
                    boardPanel.updateMalPosition(data); // BoardPanel에 말 위치 반영

                }
                case "말 잡힘" -> {
                    @SuppressWarnings("unchecked")
                    ArrayList<Integer> info = (ArrayList<Integer>) evt.getNewValue();
                    int playerId = info.get(0);
                    int malId = info.get(1);

                    boardPanel.removeMalAt(new int[] { playerId, malId });
                    boardPanel.enableMalButtonsForPlayer(yutnoriSet.getPlayerTurn());
                    throwPanel.enableAllButtons(true);
                }
                case "턴 변경됨" -> {
                    infoPanel.updatePlayerTurn((int) evt.getNewValue());
                    throwPanel.enableAllButtons(true);
                    boardPanel.disableAllMalButtons();
                }
                case "득점" -> {
                    infoPanel.showPlayerScores();
                }
            }
        });

        //게임 종료 시 알림 추가
        yutnoriSet.setOnGameEndCallback(playerTurn -> {
            JOptionPane.showMessageDialog(null,
                    "🎉 플레이어 " + (playerTurn + 1) + "이(가) 승리했습니다!",
                    "게임 종료",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // 모든 말을 시작 위치(예: 노드 0)에 배치

        System.out.println("[GamePanel] 플레이어 수: " + yutnoriSet.getPlayers().size());

        for (int playerId = 0; playerId < yutnoriSet.getPlayers().size(); playerId++) {
            System.out.println("플레이어 " + playerId + " 말 수: " +
                    yutnoriSet.getPlayers().get(playerId).getMalList().size());
        }

        for (int playerId = 0; playerId < yutnoriSet.getPlayers().size(); playerId++) {
            for (int malId = 0; malId < yutnoriSet.getPlayers().get(playerId).getMalList().size(); malId++) {
                // 항상 존재하는 노드 ID로 초기화 (예: 노드 1)
                yutnoriSet.getPlayers().get(playerId).getMalList().get(malId).setPosition(playerId * (-1));
                boardPanel.updateMalPosition(new int[] { playerId, malId, playerId * (-1) });
            }
        }


        // 4. 첫 번째 턴 UI 초기화
        infoPanel.updatePlayerTurn(yutnoriSet.getPlayerTurn());
        infoPanel.showPlayerScores();

    }
}