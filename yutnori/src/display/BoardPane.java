package display;

import GameModel.YutnoriSet;
import assets.*;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import play.Mal;
import play.Player;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.*;
import java.util.List;



public class BoardPane extends Pane implements PropertyChangeListener{

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

        // 보드 종류 선택
        switch (GameSettings.getBoardShape()) {
            case 4 -> this.boardGraph = new BoardGraph4();
            case 5 -> this.boardGraph = new BoardGraph5();
            case 6 -> this.boardGraph = new BoardGraph6();
        }

        if (this.boardGraph == null) {
            System.out.println("Board type 잘못됨: " + GameSettings.getBoardShape() + ", 기본값 4로 설정");
            this.boardGraph = new BoardGraph4();
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
            btn.setDisable(true);  // Swing의 setEnabled(false) 대응
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
                // repaint() → JavaFX에서는 생략 가능
            }
        });

        // 일반 옵저버 등록
        yutnoriSet.addObserver(this);
    }

    private Point2D toPoint2D(MyPoint point) {
        return new Point2D(point.getX(), point.getY());
    }

    protected void paintComponent(Graphics g) {

    }




    private void drawBoardGraph() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(javafx.scene.paint.Color.GRAY);
        gc.setLineWidth(2);

        // 1. 노드 간 선 (edge) 그리기
        for (int[] edge : boardGraph.getEdges()) {
            MyPoint from = boardGraph.getNodePositions().get(edge[0]);
            MyPoint to = boardGraph.getNodePositions().get(edge[1]);
            if (from != null && to != null) {
                gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
            }
        }

        // 2. 노드 원 및 번호 그리기
        for (Map.Entry<Integer, MyPoint> entry : boardGraph.getNodePositions().entrySet()) {
            int id = entry.getKey();
            MyPoint p = entry.getValue();

            double x = p.getX();
            double y = p.getY();
            double r = 15;

            // 노드 원
            gc.setFill(javafx.scene.paint.Color.WHITE);
            gc.fillOval(x - r, y - r, r * 2, r * 2);

            gc.setStroke(javafx.scene.paint.Color.BLACK);
            gc.strokeOval(x - r, y - r, r * 2, r * 2);

            // 노드 번호 텍스트
            gc.setFill(javafx.scene.paint.Color.BLACK);
            gc.setFont(javafx.scene.text.Font.font("Arial", 10));
            gc.fillText(String.valueOf(id), x - 6, y + 4);
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
        MyPoint basePos;
        if (nodeId <= 0) {
            basePos = new MyPoint(800, 150 + (playerId * 50));
        } else {
            basePos = boardGraph.getNodePositions().get(nodeId);
        }

        if (basePos == null) return;

        double labelX = basePos.getX() + 15;
        double labelY = basePos.getY() - 15;

        // 라벨 생성
        Label countLabel = new Label(String.valueOf(count));
        countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        countLabel.setTextFill(Color.BLACK);
        countLabel.setAlignment(Pos.CENTER);
        countLabel.setPrefWidth(20);
        countLabel.setPrefHeight(20);
        countLabel.setLayoutX(labelX);
        countLabel.setLayoutY(labelY);

        getChildren().add(countLabel);
        nodeCountLabels.put(labelKey, countLabel);
    }


    private void moveCaughtMalToStartNode(int team, int malNumber) {
        // 기존 말 버튼 제거
        removeMalButton(team, malNumber);

        // 시작 노드 고정 좌표
        int startNodeId = 0;
        MyPoint pos = new MyPoint(800, 150 + 50 * team);

        // 말 위치 저장
        malPositions.put(team * 10 + malNumber, new Point((int) pos.getX(), (int) pos.getY())); // 유지

        // 새 말 버튼 생성
        MalButton malBtn = new MalButton(team, malNumber, playerColors.get(team));
        malBtn.setNodeId(startNodeId);
        malBtn.setLayoutX(pos.getX() - 10);
        malBtn.setLayoutY(pos.getY() - 10);
        malBtn.setOnAction(e -> handleMalClick(team, malNumber, startNodeId));

        getChildren().add(malBtn);
        malBtn.toFront();  // 최상단 배치
        malButtons.add(malBtn);

        // 시작 노드의 라벨 갱신
        updateNodeCountLabel(startNodeId, team);
    }

    public void updateMalPosition(int[] data) {
        int playerId = data[0];
        int malId = data[1];
        int nodeId = data[2];

        // 도착 노드가 종료 지점이면 말 제거
        if (nodeId > 0 && yutnoriSet.board.boardShape.get(nodeId).isEndPoint()) {
            removeMalButton(playerId, malId);
            malPositions.remove(playerId * 10 + malId);
            return;
        }

        // 위치 계산
        MyPoint pos;
        if (nodeId <= 0) {
            pos = new MyPoint(800, 150 + 50 * playerId);
        } else {
            Point raw = boardGraph.getNodePositions().get(nodeId);
            if (raw == null) return;
            pos = new MyPoint(raw.x, raw.y);
        }

        // 기존 말 버튼 제거
        removeMalButton(playerId, malId);

        // 현재 노드에 이미 같은 팀 말이 있으면 대표 말만 표시
        long count = malButtons.stream()
                .filter(m -> m.getNodeId() == nodeId && m.getPlayerId() == playerId)
                .count();

        if (count >= 1 && nodeId > 0) {
            // 이미 대표 말이 있으면 추가하지 않음
            updateNodeCountLabel(nodeId, playerId); // 라벨만 갱신
            return;
        }

        // 대표 말 버튼 생성
        MalButton malBtn = new MalButton(playerId, malId, playerColors.get(playerId));
        malBtn.setNodeId(nodeId);
        malBtn.setLayoutX(pos.getX() - 10);
        malBtn.setLayoutY(pos.getY() - 10);
        malBtn.setOnAction(e -> handleMalClick(playerId, malId, nodeId));

        getChildren().add(malBtn);
        malBtn.toFront();
        malButtons.add(malBtn);
        malPositions.put(playerId * 10 + malId, new Point((int) pos.getX(), (int) pos.getY()));

        // 말 버튼 추가 후 말 개수 라벨 갱신
        updateNodeCountLabel(nodeId, playerId);
    }

    private void removeMalButton(int playerId, int malId) {
        MalButton removed = null;
        int nodeId = -999;

        for (MalButton btn : malButtons) {
            if (btn.getPlayerId() == playerId && btn.getMalId() == malId) {
                removed = btn;
                nodeId = btn.getNodeId();
                getChildren().remove(btn); // JavaFX에서 화면에서 제거
                break;
            }
        }

        if (removed != null) {
            malButtons.remove(removed);
            malPositions.remove(playerId * 10 + malId);

            // 말 개수 라벨 갱신
            updateNodeCountLabel(nodeId, playerId);
        }
    }

    public void disableAllMalButtons() {
        for (MalButton btn : malButtons) {
            btn.setDisable(true);
        }
    }

    public void enableMalButtonsForPlayer(int playerId) {
        for (MalButton btn : malButtons) {
            if (btn.getPlayerId() == playerId) {
                btn.setDisable(false);  // 활성화
            } else {
                btn.setDisable(true);   // 비활성화
            }
        }
    }

    private void handleMalClick(int playerId, int malId, int currentNode) {

    }


    private void handleNodeClick(int nodeId, YutResult result) {
        boolean keepTurn = yutnoriSet.moveMal(selectedPlayerId, selectedMalId, nodeId, result);

        updateMalPosition(new int[]{selectedPlayerId, selectedMalId, nodeId});

        for (NodeButton btn : nodeButtons.values()) {
            btn.setHighlighted(false);
            btn.setDisable(true);
            btn.setOnAction(null);  // 모든 액션 제거
        }

        // JavaFX는 repaint 불필요 (자동 렌더링됨)

        if (!keepTurn) {
            System.out.println("[handleNodeClick] 턴 종료: 다음 플레이어로 넘어갑니다.");
            yutnoriSet.setYutResult_to_use(null);
            yutnoriSet.nextTurn();
        } else {
            yutnoriSet.setYutResult_to_use(null);
            System.out.println("[handleNodeClick] 턴 유지됨: 잡기 또는 윷/모로 추가 턴!");
        }
    }

    public void removeMalAt(int[] data) {
        int playerId = data[0];
        int malId = data[1];

        malPositions.remove(playerId * 10 + malId); // 말 위치 맵에서 제거

        // JavaFX는 자동으로 다시 그려지므로 repaint()는 불필요하지만,
        // 강제로 새로고침이 필요할 경우 layout 갱신을 호출할 수 있음
        this.requestLayout(); // 또는 this.layout()으로 대체 가능
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();

        // 🟥 잡기 이벤트 처리
        if (property.equals("말 잡힘")) {
            ArrayList<Integer> info = (ArrayList<Integer>) evt.getNewValue();
            int playerTurn = info.get(0);
            int nodeId = info.get(1);

            System.out.println("[BoardPanel] 말 잡힘 알림 수신: player " + playerTurn + ", node " + nodeId);

            for (Player p : yutnoriSet.getPlayers()) {
                for (Mal m : p.getMalList()) {
                    if (m.getPosition() == 0) {
                        moveCaughtMalToStartNode(m.getTeam(), m.getMalNumber());
                    }
                }
            }

            this.requestLayout();
        }

        // 🟦 말 이동 시 UI 업데이트
        else if (property.equals("말 이동됨")) {
            int[] data = (int[]) evt.getNewValue();
            int playerId = data[0];
            int malId = data[1];
            int nodeId = data[2];

            updateMalPosition(new int[]{playerId, malId, nodeId});
            this.requestLayout();
        }

        // 🟩 게임 종료 처리


    }




}
