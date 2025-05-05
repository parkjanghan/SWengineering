package display;

import GameController.YutnoriSet;
import play.YutResult;
import display.BoardPanel;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final YutnoriSet yutnoriSet;

    public GamePanel(YutnoriSet yutnoriSet) {
        setLayout(new BorderLayout());
        this.yutnoriSet = yutnoriSet;
        this.yutnoriSet.startGame(GameSettings.getPlayerCount(), GameSettings.getMalCount()); // ✅ 2인용, 말 4개로 초기화 추가

        // 1. 각 서브 패널에 yutnoriSet 전달
        BoardPanel boardPanel = new BoardPanel(yutnoriSet);
        ThrowPanel throwPanel = new ThrowPanel(yutnoriSet);
        PlayerInfoPanel infoPanel = new PlayerInfoPanel(yutnoriSet);

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
                case "윷 던지기 결과" -> throwPanel.showYutResult((YutResult) evt.getNewValue());
                case "말 이동됨" -> {
                    int[] data = (int[]) evt.getNewValue(); // [playerId, malId, destNodeId]
                    boardPanel.updateMalPosition(data); // BoardPanel에 말 위치 반영
                }
                case "말 잡힘" -> boardPanel.removeMalAt((int[]) evt.getNewValue());
                case "턴 변경됨" -> infoPanel.updatePlayerTurn((int) evt.getNewValue());
            }
        });

        // 모든 말을 시작 위치(예: 노드 0)에 배치

        System.out.println("[GamePanel] 플레이어 수: " + yutnoriSet.getPlayers().size());

        for (int playerId = 0; playerId < yutnoriSet.getPlayers().size(); playerId++) {
            System.out.println("플레이어 " + playerId + " 말 수: " +
                    yutnoriSet.getPlayers().get(playerId).getMalList().size());
        }

        for (int playerId = 0; playerId < yutnoriSet.getPlayers().size(); playerId++) {
            for (int malId = 0; malId < yutnoriSet.getPlayers().get(playerId).getMalList().size(); malId++) {
                yutnoriSet.getPlayers().get(playerId).getMalList().get(malId).setPosition(0);

                // 위치 계산: 노드 0의 좌표 + y축 아래로 50px
                Point node0 = boardPanel.getBoardGraph().getNodePositions().get(0);
                Point initial = new Point(node0.x, node0.y + 50);

                boardPanel.addInitialMalButton(playerId, malId, initial);
            }
        }


        // 4. 첫 번째 턴 UI 초기화
        infoPanel.updatePlayerTurn(yutnoriSet.getPlayerTurn());

    }
}