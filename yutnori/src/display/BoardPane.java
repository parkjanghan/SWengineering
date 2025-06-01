package display;

import GameModel.GameSettings;
import GameModel.YutnoriSet;
import assets.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import play.Mal;
import play.Player;
import play.YutResult;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class BoardPane extends Pane implements PropertyChangeListener {

    private BoardGraph boardGraph;
    private YutnoriSet yutnoriSet;

    // JavaFX의 위치 표현은 Point
    private final Map<Integer, Point> malPositions = new HashMap<>();

    // JavaFX의 Color 사용
    private final Map<Integer, Color> playerColors = Map.of(
            0, Color.RED,
            1, Color.BLUE,
            2, Color.GREEN,
            3, Color.MAGENTA
    );

    private int selectedPlayerId = -1;
    private int selectedMalId = -1;

    private final Map<Integer, NodeButton> nodeButtons = new HashMap<>();
    private final List<MalButton> malButtons = new ArrayList<>();

    private final Map<String, Label> nodeCountLabels = new HashMap<>();

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
        for (Map.Entry<Integer, Point> entry : boardGraph.getNodePositions().entrySet()) {
            int nodeId = entry.getKey();
            Point raw = entry.getValue();
            Point pos = new Point(raw.getX(), raw.getY());

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
                            removeMalAt(new int[]{m.getTeam(), m.getMalNumber()});
                            updateMalPosition(new int[]{m.getTeam(), m.getMalNumber(), 0});
                        }
                    }
                }
            }
        });

        // 일반 옵저버 등록
        yutnoriSet.addObserver(this);
    }

    private void drawMals() {
        // 기존 말 UI 제거
        this.getChildren().removeIf(node -> node instanceof javafx.scene.shape.Circle);

        for (Map.Entry<Integer, Point> entry : malPositions.entrySet()) {
            int key = entry.getKey();
            int playerId = key / 10;
            Point pos = entry.getValue();

            Color color = playerColors.getOrDefault(playerId, Color.BLACK);

            // JavaFX 원 그리기
            javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle();
            circle.setRadius(7); // 반지름 14의 절반
            circle.setCenterX(pos.getX());
            circle.setCenterY(pos.getY());
            circle.setFill(color);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(1.5);

            this.getChildren().add(circle);
        }
    }

    private void updateBoard() {
        // 말, 버튼 등 기존 노드 초기화
        this.getChildren().removeIf(node -> node instanceof MalButton || node instanceof javafx.scene.shape.Circle);

        // 노드 버튼 다시 배치
        for (NodeButton btn : nodeButtons.values()) {
            this.getChildren().add(btn);
        }

        // 말 위치 저장된 것 기준으로 다시 그림
        drawMals();
    }

    private void drawBoardGraph() {
        // 간선(선) 먼저 그림
        for (int[] edge : boardGraph.getEdges()) {
            Point from = boardGraph.getNodePositions().get(edge[0]);
            Point to = boardGraph.getNodePositions().get(edge[1]);

            if (from != null && to != null) {
                Line line = new Line(from.getX(), from.getY(), to.getX(), to.getY());
                line.setStroke(Color.GRAY);
                line.setStrokeWidth(2.0);
                this.getChildren().add(line);
            }
        }

        // 노드(원 + 텍스트)
        for (Map.Entry<Integer, Point> entry : boardGraph.getNodePositions().entrySet()) {
            int id = entry.getKey();
            Point p = entry.getValue();

            // 노드 원
            Circle circle = new Circle(p.getX(), p.getY(), 15);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            this.getChildren().add(circle);

            // 노드 ID 라벨
            Label label = new Label(String.valueOf(id));
            label.setStyle("-fx-font-size: 10px; -fx-font-family: Arial;");
            label.setLayoutX(p.getX() - 6); // 위치 보정
            label.setLayoutY(p.getY() - 7);
            this.getChildren().add(label);
        }
    }

    private void moveCaughtMalToStartNode(int team, int malNumber) {
        // 팀마다 다른 시작 노드 번호 (예: -1, -2, ...)
        int startNode = team * (-1);

        // 말의 위치 정보를 논리적으로 이동
        yutnoriSet.getPlayers().get(team).getMalList().get(malNumber).setPosition(startNode);

        // 이 메서드 안에서 UI 업데이트까지 포함됨
        updateMalPosition(new int[]{team, malNumber, startNode});

        System.out.println("[BoardPane] 말이 시작 위치로 이동됨: team " + team + ", mal " + malNumber + ", node " + startNode);
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

        // 도착지점이면 말 제거
        if (nodeId > 0 && yutnoriSet.board.boardShape.get(nodeId).isEndPoint()) {
            removeMalButton(playerId, malId);
            malPositions.remove(playerId * 10 + malId);
            return;
        }

        // 좌표 계산
        Point nodePos;
        if (nodeId <= 0) {
            nodePos = new Point(800, 150 + (playerId * 50)); // 대기 위치
        } else {
            nodePos = boardGraph.getNodePositions().get(nodeId);
            if (nodePos == null) return;
        }

        // 기존 버튼 제거
        removeMalButton(playerId, malId);

        // 같은 위치에 이미 같은 플레이어의 말이 있으면 숫자만 갱신
        long count = malButtons.stream()
                .filter(m -> m.getNodeId() == nodeId && m.getPlayerId() == playerId)
                .count();

        if (count >= 1 && nodeId > 0) {
            updateNodeCountLabel(nodeId, playerId);
            return;
        }

        // 새 말 버튼 생성
        Color color = playerColors.get(playerId);
        MalButton malButton = new MalButton(playerId, malId, color);
        malButton.setLayoutX(nodePos.getX() - 10);
        malButton.setLayoutY(nodePos.getY() - 10);
        malButton.setNodeId(nodeId);

        malButton.getButton().setOnAction(e -> {
            if (yutnoriSet.getPlayerTurn() == playerId &&
                    yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_SELECT) {
                handleMalClick(playerId, malId, nodeId);
            }
        });

        malButtons.add(malButton);
        malPositions.put(playerId * 10 + malId, nodePos);
        getChildren().add(malButton);
        updateNodeCountLabel(nodeId, playerId);
    }


    private void removeMalButton(int playerId, int malId) {
        MalButton removed = null;
        int nodeId = -999;

        for (MalButton btn : malButtons) {
            if (btn.getPlayerId() == playerId && btn.getMalId() == malId) {
                removed = btn;
                nodeId = btn.getNodeId();
                getChildren().remove(btn); // JavaFX에서 말 버튼 제거
                break;
            }
        }

        if (removed != null) {
            malButtons.remove(removed); // 리스트에서도 제거
            malPositions.remove(playerId * 10 + malId); // 위치 정보 제거
            updateNodeCountLabel(nodeId, playerId); // 라벨 갱신
        }
    }

    private void updateNodeCountLabel(int nodeId, int playerId) {
        int labelNodeId = (nodeId <= 0) ? 0 : nodeId;
        String labelKey = playerId + ":" + labelNodeId;

        // 기존 라벨 제거
        Label oldLabel = nodeCountLabels.get(labelKey);
        if (oldLabel != null) {
            getChildren().remove(oldLabel);
            nodeCountLabels.remove(labelKey);
        }

        // 말 개수 계산
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

        // 위치 계산
        Point basePos;
        if (nodeId <= 0) {
            basePos = new Point(800, 150 + (playerId * 50));
        } else {
            basePos = boardGraph.getNodePositions().get(nodeId);
        }

        if (basePos == null) return;

        double labelX = basePos.getX() + 15;
        double labelY = basePos.getY() - 15;

        // 새 라벨 생성
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

        // ❗ 반드시 추가해야 라벨이 보임
        getChildren().add(countLabel);
        nodeCountLabels.put(labelKey, countLabel);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();

        // 잡기 이벤트 처리
        if (property.equals("말 잡힘")) {
            System.out.println("[BoardPane] 말 잡힘 알림 수신");
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
            Object value = evt.getNewValue();
            if (value instanceof int[]) {
                int[] data = (int[]) value;
                int playerTurn = data[0];

                Stage parentStage = (Stage) this.getScene().getWindow();

                // 🎉 승자 안내 라벨
                Label winnerLabel = new Label("🎉 플레이어 " + (playerTurn + 1) + "이(가) 승리했습니다!");
                winnerLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: '맑은 고딕';");
                winnerLabel.setAlignment(Pos.CENTER);
                winnerLabel.setPrefWidth(260);

                // JavaFX 다이얼로그(Stage) 생성
                Stage dialog = new Stage();
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.initOwner(parentStage); // ⚠️ 현재 루트 스테이지 필요
                dialog.setTitle("게임 종료");
                dialog.setWidth(320);
                dialog.setHeight(160);
                dialog.setResizable(false);

                // 다시하기 버튼
                Button restartBtn = new Button("다시하기");
                restartBtn.setPrefSize(100, 35);
                restartBtn.setOnAction(e -> {
                    dialog.close();
                    Yutnori.getInstance().goToIntro(); // ✅ 바로 호출 가능
                });

                // 종료하기 버튼
                Button exitBtn = new Button("종료하기");
                exitBtn.setPrefSize(100, 35);
                exitBtn.setOnAction(e -> {
                    dialog.close();
                    System.exit(0);
                });

                // 버튼 배치
                HBox buttonBox = new HBox(20, restartBtn, exitBtn);
                buttonBox.setAlignment(Pos.CENTER);

                // 전체 레이아웃 구성
                VBox layout = new VBox(15, winnerLabel, buttonBox);
                layout.setAlignment(Pos.CENTER);
                layout.setPadding(new Insets(10));

                Scene scene = new Scene(layout);
                dialog.setScene(scene);
                dialog.showAndWait();

                System.out.println("[BoardPanelFX] 게임 종료 알림 수신: player " + playerTurn);
            } else {
                System.err.println("⚠️ '게임 종료' 이벤트 타입 불일치: " + value.getClass().getName());
            }
        }
    }

    public void removeMalAt(int[] data) {
        int playerId = data[0];
        int malId = data[1];

        System.out.println("[BoardPane] 말 제거 요청: player " + playerId + ", mal " + malId);
        malPositions.remove(playerId * 10 + malId);

    }

    public void disableAllMalButtons() {
        for (MalButton btn : malButtons) {
            btn.setDisable(true);  // JavaFX에서는 setDisable(true)
        }
    }

    public void enableMalButtonsForPlayer(int playerId) {
        for (MalButton btn : malButtons) {
            if (btn.getPlayerId() == playerId) {
                btn.setDisable(false); // 활성화
            } else {
                btn.setDisable(true);  // 비활성화
            }
        }
    }
}



