package display;

import GameModel.YutnoriSet;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import play.YutResult;

import java.util.ArrayList;

public class GamePane extends BorderPane {

    private final YutnoriSet yutnoriSet;
    private BoardPane boardPanel;
    private ThrowPane throwPanel;
    private PlayerInfoPane infoPanel;

    public GamePane(YutnoriSet yutnoriSet) {
        this.yutnoriSet = yutnoriSet;
        this.yutnoriSet.startGame(GameSettings.getPlayerCount(), GameSettings.getMalCount());

        // 패널 구성
        boardPanel = new BoardPane(yutnoriSet);
        throwPanel = new ThrowPane(yutnoriSet);
        infoPanel = new PlayerInfoPane(yutnoriSet);

        VBox rightPanel = new VBox();
        rightPanel.setSpacing(10);
        rightPanel.getChildren().addAll(infoPanel, throwPanel);

        setCenter(boardPanel);
        setRight(rightPanel);

        // 옵저버 등록
        yutnoriSet.addObserver(evt -> {
            switch (evt.getPropertyName()) {
                case "모/윷이 나옴" -> {
                    throwPanel.showYutResult((YutResult) evt.getNewValue());
                    throwPanel.enableAllButtons(true);
                }
                case "윷 던지기 결과" -> {
                    throwPanel.showYutResult((YutResult) evt.getNewValue());
                    //boardPanel.enableMalButtonsForPlayer(yutnoriSet.getPlayerTurn());
                    throwPanel.enableAllButtons(false);
                }
                case "사용할 결과 선택" -> {
                    throwPanel.enableAllButtons(true);
                    throwPanel.updateResultDisplay();
                    throwPanel.enableAllButtons(false);
                }
                case "말 이동됨" -> {
                    int[] data = (int[]) evt.getNewValue(); // [playerId, malId, destNodeId]
                    //boardPanel.updateMalPosition(data);
                }
                case "말 잡힘" -> {
                    @SuppressWarnings("unchecked")
                    ArrayList<Integer> info = (ArrayList<Integer>) evt.getNewValue();
                    int playerId = info.get(0);
                    int malId = info.get(1);
                    //boardPanel.removeMalAt(new int[]{playerId, malId});
                    //boardPanel.enableMalButtonsForPlayer(yutnoriSet.getPlayerTurn());
                    throwPanel.enableAllButtons(true);
                }
                case "턴 변경됨" -> {
                    int newTurn = (int) evt.getNewValue();
                    infoPanel.updatePlayerTurn(newTurn);
                    throwPanel.enableAllButtons(true);
                    //boardPanel.disableAllMalButtons();
                }
                case "득점" -> {
                    infoPanel.showPlayerScores();
                }
            }
        });
        // 3. 모든 말을 시작 위치(예: 노드 -1, -2, ...)에 배치
        System.out.println("[GamePanel] 플레이어 수: " + yutnoriSet.getPlayers().size());

        for (int playerId = 0; playerId < yutnoriSet.getPlayers().size(); playerId++) {
            System.out.println("플레이어 " + playerId + " 말 수: " +
                    yutnoriSet.getPlayers().get(playerId).getMalList().size());
        }

        for (int playerId = 0; playerId < yutnoriSet.getPlayers().size(); playerId++) {
            for (int malId = 0; malId < yutnoriSet.getPlayers().get(playerId).getMalList().size(); malId++) {
                int startNode = playerId * (-1);
                yutnoriSet.getPlayers().get(playerId).getMalList().get(malId).setPosition(startNode);
                //boardPanel.updateMalPosition(new int[]{playerId, malId, startNode});
            }
        }

        // 4. 첫 번째 턴 UI 초기화
        infoPanel.updatePlayerTurn(yutnoriSet.getPlayerTurn());
        infoPanel.showPlayerScores();

    }
}
