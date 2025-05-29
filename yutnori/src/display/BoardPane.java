package display;

import GameModel.YutnoriSet;
import assets.*;

import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import play.Mal;
import play.Player;
import play.YutResult;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class BoardPane extends Pane implements PropertyChangeListener {

    private final YutnoriSet yutnoriSet;
    private BoardGraph boardGraph;
    private final Map<Integer, NodeButton> nodeButtons = new HashMap<>();
    private final List<MalButton> malButtons = new ArrayList<>();
    private final Map<Integer, Point2D> malPositions = new HashMap<>();
    private final Map<String, Label> nodeCountLabels = new HashMap<>();

    private int selectedPlayerId = -1;
    private int selectedMalId = -1;

    private final Map<Integer, Color> playerColors = Map.of(
            0, Color.RED, 1, Color.BLUE, 2, Color.GREEN, 3, Color.MAGENTA
    );

    public BoardPane(YutnoriSet yutnoriSet) {
        this.yutnoriSet = yutnoriSet;
        System.out.println("BoardPane created");

        // 보드 종류 선택
        switch (GameSettings.getBoardShape()) {
            case 4 -> this.boardGraph = new BoardGraph4();
            case 5 -> this.boardGraph = new BoardGraph5();
            case 6 -> this.boardGraph = new BoardGraph6();
            default -> {
                System.out.println("Board type 잘못됨: " + GameSettings.getBoardShape() + ", 기본값 4로 설정");
                this.boardGraph = new BoardGraph4();
            }
        }

        // 배경 색상 설정
        setPrefSize(1000, 720);
        setStyle("-fx-background-color: rgb(240, 240, 240);");

        // 노드 버튼 생성 및 추가
        for (Map.Entry<Integer, MyPoint> entry : boardGraph.getNodePositions().entrySet()) {
            int nodeId = entry.getKey();
            MyPoint raw = entry.getValue();
            Point2D pos = new Point2D(raw.getX(), raw.getY());

            NodeButton btn = new NodeButton(nodeId, pos);
            btn.setDisable(true);
            nodeButtons.put(nodeId, btn);
            getChildren().add(btn);
        }

        // "말 잡힘" 옵저버 등록
        yutnoriSet.addObserver(evt -> {
            if ("말 잡힘".equals(evt.getPropertyName())) {
                @SuppressWarnings("unchecked")
                ArrayList<Integer> info = (ArrayList<Integer>) evt.getNewValue();
                int playerId = info.get(0);
                int nodeId = info.get(1);

                System.out.println("[BoardPanel] 말 잡힘 알림 수신: player " + playerId + ", node " + nodeId);

                for (Player p : yutnoriSet.getPlayers()) {
                    for (Mal m : p.getMalList()) {
                        if (m.getPosition() == 0) {
                            updateMalPosition(new int[]{m.getTeam(), m.getMalNumber(), 0});
                        }
                    }
                }
            }
        });

        // 일반 옵저버 등록
        yutnoriSet.addObserver(this);
    }

    private void handleMalClick(int playerId, int malId, int currentNode) {
        if (playerId != yutnoriSet.getPlayerTurn()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("턴 알림");
            alert.setHeaderText(null);
            alert.setContentText("[BoardPane]-handleMalClick: 지금은 플레이어 " + (yutnoriSet.getPlayerTurn() + 1) + "의 턴입니다.");
            alert.showAndWait();
            return;
        }

        if (selectedPlayerId != -1 && selectedMalId != -1) {
            for (MalButton btn : malButtons) {
                if (btn.getPlayerId() == selectedPlayerId && btn.getMalId() == selectedMalId) {
                    btn.setDisable(false); // 이전 선택된 말 버튼 활성화
                }
            }
        }

        this.selectedPlayerId = playerId;
        this.selectedMalId = malId;

        // 모든 노드 버튼 초기화
        for (NodeButton b : nodeButtons.values()) {
            b.setHighlighted(false);
            b.setDisable(true);
            // JavaFX에서는 setOnAction으로 이벤트 핸들러를 제거
            b.setOnAction(null);
        }

        System.out.println("[BoardPane][handleMalClick] playerResults: " + yutnoriSet.getPlayerResults());

        // 움직임을 먼저 선택해야 이전에 있었던 움직임들이 반영이 안됨
        // 즉 사용할 yutResult를 먼저 고르게 해야 굴린 결과들을 사용 할 수 있음
        for (MalButton btn : malButtons) {
            if (btn.getPlayerId() == playerId) {
                btn.setDisable(false);
            } else {
                btn.setDisable(true);
            }
        } // 말 버튼들 활성화/비활성화

        // 움직일 윷 yutResult 선택하기
        YutResult result = yutnoriSet.getYutResult_to_use();
        List<Integer> moveable = yutnoriSet.showMoveableNodeId(currentNode, result);

        if (moveable == null) {
            System.out.println("[BoardPane][handleMalClick] 이동할 수 있는 노드 없음");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("이동 불가");
            alert.setHeaderText(null);
            alert.setContentText("사용할 결과를 먼저 선택하세요");
            alert.showAndWait();
        } else {
            for (int nodeId : moveable) {
                NodeButton btn = nodeButtons.get(nodeId);
                if (btn != null) {
                    btn.setHighlighted(true);
                    btn.setDisable(false);
                    // JavaFX에서는 setOnAction으로 이벤트 핸들러 설정
                    btn.setOnAction(e -> handleNodeClick(nodeId, result));
                }
            }
        }

        // JavaFX에서는 보통 Scene의 루트 노드에서 repaint가 자동으로 처리됨
        // 필요하다면 특정 노드에서 requestLayout() 또는 autoSizeChildren() 호출
    }

    private void handleNodeClick(int nodeId, YutResult result) {
        boolean keepTurn = yutnoriSet.moveMal(selectedPlayerId, selectedMalId, nodeId, result);
        updateMalPosition(new int[]{selectedPlayerId, selectedMalId, nodeId});

        // 모든 노드 버튼 초기화
        for (NodeButton btn : nodeButtons.values()) {
            btn.setHighlighted(false);
            btn.setDisable(true);
            // JavaFX에서는 setOnAction으로 이벤트 핸들러를 제거
            btn.setOnAction(null);
            //System.out.println("[BoardPane][handleNodeClick] 노드 버튼 초기화: " + btn.getNodeId());
        }

        // JavaFX에서는 보통 Scene의 루트 노드에서 repaint가 자동으로 처리됨
        // 필요하다면 특정 노드에서 requestLayout() 또는 autoSizeChildren() 호출

        if (!keepTurn) {
            System.out.println("[BoardPane] [handleNodeClick] 턴 종료: 다음 플레이어로 넘어갑니다.");
            System.out.println("[BoardPane] [handleNodeClick] 현재 플레이어: " + yutnoriSet.getPlayerTurn()+ "flag: " + yutnoriSet.getInGameFlag());
            yutnoriSet.setYutResult_to_use(null);
            yutnoriSet.nextTurn();
        }
        else {

            yutnoriSet.setYutResult_to_use(null);
            System.out.println("[handleNodeClick] 턴 유지됨: 잡기 또는 윷/모로 추가 턴!");
        }
    }
    public void updateMalPosition(int[] data) {
        int playerId = data[0];
        int malId = data[1];
        int nodeId = data[2];

        if (nodeId > 0 && yutnoriSet.board.boardShape.get(nodeId).isEndPoint()) {
            removeMalButton(playerId, malId);
            malPositions.remove(playerId * 10 + malId);
            return;
        }

        Point2D position;
        if (nodeId <= 0) {
            position = new Point2D(800, 150 + (playerId * 50));
        } else {
            MyPoint nodePos = boardGraph.getNodePositions().get(nodeId);
            if (nodePos == null) return;
            position = new Point2D(nodePos.getX(), nodePos.getY());
        }

        removeMalButton(playerId, malId);

        long count = malButtons.stream()
                .filter(m -> m.getNodeId() == nodeId && m.getPlayerId() == playerId)
                .count();

        if (count >= 1 && nodeId > 0) {
            updateNodeCountLabel(nodeId, playerId);
            return;
        }

        Color playerColor = playerColors.get(playerId);
        MalButton malButton = new MalButton(playerId, malId, playerColor);
        malButton.setLayoutX(position.getX() - 10);
        malButton.setLayoutY(position.getY() - 10);
        malButton.setNodeId(nodeId);

        malButton.getButton().setOnAction(e -> {
            if (yutnoriSet.getPlayerTurn() == playerId &&
                    yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_SELECT) {
                handleMalClick(playerId, malId, nodeId);
            }
        });

        malButtons.add(malButton);
        malPositions.put(playerId * 10 + malId, position);
        getChildren().add(malButton);

        updateNodeCountLabel(nodeId, playerId);
    }

    private void removeMalButton(int playerId, int malId) {
        int key = playerId * 10 + malId;
        MalButton existingMalButton = null;

        for (MalButton mb : malButtons) {
            if (mb.getPlayerId() == playerId && mb.getMalId() == malId) {
                existingMalButton = mb;
                break;
            }
        }

        if (existingMalButton != null) {
            getChildren().remove(existingMalButton);
            malButtons.remove(existingMalButton);
            malPositions.remove(key);
            System.out.println("[BoardPane] 말 제거됨: player " + playerId + ", mal " + malId);
        }
    }

    private void updateNodeCountLabel(int nodeId, int playerId) {
        int labelNodeId = (nodeId <= 0) ? 0 : nodeId;
        String labelKey = playerId + ":" + labelNodeId;

        Label oldLabel = nodeCountLabels.get(labelKey);
        if (oldLabel != null) {
            getChildren().remove(oldLabel);
            nodeCountLabels.remove(labelKey);
        }

        long count;
        if (nodeId <= 0) {
            count = yutnoriSet.getPlayers().get(playerId).getMalList().stream()
                    .filter(m -> m.getPosition() <= 0)
                    .count();
        } else {
            count = yutnoriSet.getBoard()
                    .boardShape
                    .get(nodeId)
                    .getOccupyingPieces()
                    .stream()
                    .filter(m -> m.getTeam() == playerId)
                    .count();
        }

        if (count < 1) return;

        MyPoint basePos;
        if (nodeId <= 0) {
            basePos = new MyPoint(800, 150 + (playerId * 50));
        } else {
            basePos = boardGraph.getNodePositions().get(nodeId);
        }

        if (basePos == null) return;

        double labelX = basePos.getX() + 15;
        double labelY = basePos.getY() - 15;

        Label countLabel = new Label(String.valueOf(count));
        countLabel.setLayoutX(labelX);
        countLabel.setLayoutY(labelY);
        String color;
        switch (playerId) {
            case 0 -> color = "blue";
            case 1 -> color = "red";
            case 2 -> color = "green";
            case 3 -> color = "magenta";
            default -> color = "black";
        }

        countLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();

        // 잡기 이벤트 처리
        if (property.equals("말 잡힘")) {
            ArrayList<Integer> info = (ArrayList<Integer>) evt.getNewValue();
            int playerTurn = info.get(0);
            int nodeId = info.get(1);

            System.out.println("[BoardPanel] 말 잡힘 알림 수신: player " + playerTurn + ", node " + nodeId);

            for (Player p : yutnoriSet.getPlayers()) {
                for (Mal m : p.getMalList()) {
                    if (m.getPosition() <= 0) {
                        moveCaughtMalToStartNode(m.getTeam(), m.getMalNumber());
                    }
                }
            }

            this.requestLayout();
        }

        // 말 이동 시 UI 업데이트
        else if (property.equals("말 이동됨")) {
            int[] data = (int[]) evt.getNewValue();
            int playerId = data[0];
            int malId = data[1];
            int nodeId = data[2];

            updateMalPosition(new int[]{playerId, malId, nodeId});
            this.requestLayout();
        }

        // 턴 변경 이벤트 처리
        else if (property.equals("턴 변경")) {
            int newPlayerTurn = yutnoriSet.getPlayerTurn();
            System.out.println("[BoardPanel] 턴 변경 알림: 새로운 플레이어 " + newPlayerTurn);

            // Reset UI state
            disableAllMalButtons();


            if (yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_SELECT) {
                enableMalButtonsForPlayer(newPlayerTurn);
                System.out.println("[BoardPanel] 플레이어 " + newPlayerTurn + " 말 선택 가능 상태로 설정");
            }
            // NEED_TO_MOVE 상태라면 윷 결과가 있을 때만 활성화
            else if (yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_MOVE &&
                    yutnoriSet.getPlayerResults() != null &&
                    !yutnoriSet.getPlayerResults().isEmpty()) {
                enableMalButtonsForPlayer(newPlayerTurn);
                System.out.println("[BoardPanel] 플레이어 " + newPlayerTurn + " 말 이동 가능 상태로 설정");
            }
        }

        // 윷 던지기 결과 처리
        else if (property.equals("윷 결과")) {
            int currentPlayer = yutnoriSet.getPlayerTurn();
            System.out.println("[BoardPanel] 윷 결과 알림: 플레이어 " + currentPlayer);
            System.out.println("[BoardPanel] 현재 게임 상태: " + yutnoriSet.getInGameFlag());

            // 윷 결과가 나온 후 말 선택/이동이 가능한 상태가 되면 말 버튼 활성화
            if (yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_SELECT ||
                    yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_MOVE) {
                enableMalButtonsForPlayer(currentPlayer);
                System.out.println("[BoardPanel] 윷 결과 후 플레이어 " + currentPlayer + " 말 버튼 활성화");
            }
        }

        // 게임 종료 처리
        else if (property.equals("게임 종료")) {
            System.out.println("[BoardPanel] 게임 종료");
            disableAllMalButtons();
            // Add any game end UI updates here

        }
    }

    private void moveCaughtMalToStartNode(int team, int malNumber) {
        int startNode = team * (-1);
        yutnoriSet.getPlayers().get(team).getMalList().get(malNumber).setPosition(startNode);
        updateMalPosition(new int[]{team, malNumber, startNode});
        System.out.println("[BoardPane] 말이 시작 위치로 이동됨: team " + team + ", mal " + malNumber + ", node " + startNode);
    }

    public void removeMalAt(int[] ints) {
        int playerId = ints[0];
        int malId = ints[1];

        System.out.println("[BoardPane] 말 제거 요청: player " + playerId + ", mal " + malId);
        removeMalButton(playerId, malId);

        // 말이 제거되면 해당 노드의 말 개수 레이블도 업데이트
        for (NodeButton btn : nodeButtons.values()) {
            if (btn.getNodeId() == ints[2]) {
                updateNodeCountLabel(ints[2], playerId);
                break;
            }
        }
    }

    public void enableMalButtonsForPlayer(int playerTurn) {
        System.out.println("[BoardPane] 플레이어 " + playerTurn + "의 말 버튼 활성화");

        for (MalButton malButton : malButtons) {
            if (malButton.getPlayerId() == playerTurn) {
                malButton.setDisable(false);
                malButton.getButton().setOnAction(e -> {
                    int currentNode = malButton.getNodeId();
                    handleMalClick(playerTurn, malButton.getMalId(), currentNode);
                });
            } else {
                malButton.setDisable(true);
            }
        }
    }

    public void disableAllMalButtons() {
        System.out.println("[BoardPane][disableAllMalButtons] 모든 말 버튼 비활성화");

        for (MalButton malButton : malButtons) {
            malButton.setDisable(true);
            malButton.getButton().setOnAction(null);
        }

        selectedPlayerId = -1;
        selectedMalId = -1;

        for (NodeButton btn : nodeButtons.values()) {
            btn.setHighlighted(false);
            btn.setDisable(true);
            btn.setOnAction(null);
        }
    }
}


