package display;

import GameController.YutnoriSet;
import assets.BoardGraph4;
import assets.BoardGraph5;
import assets.BoardGraph6;
import play.YutResult;
import assets.BoardGraph;
import play.Mal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class BoardPanel extends JPanel {

    private BoardGraph boardGraph;
    private YutnoriSet yutnoriSet; // YutnoriSet 객체를 통해 게임 상태를 관리
    private Map<Integer, Point> malPositions = new HashMap<>(); // key = playerId * 10 + malId
    private final Map<Integer, Color> playerColors = Map.of(0, Color.RED, 1, Color.BLUE, 2, Color.GREEN, 3, Color.MAGENTA);
    private int selectedPlayerId = -1;
    private int selectedMalId = -1;
    private Set<Integer> highlightedNodes = new HashSet<>();
    private final Map<Integer, NodeButton> nodeButtons = new HashMap<>();
    private final List<MalButton> malButtons = new ArrayList<>();
    // 노드별 (playerId -> malId 리스트)
    private final Map<Integer, Map<Integer, List<Integer>>> nodeMalMap = new HashMap<>();
    // 노드에 겹친 말 수 표시용 라벨
    private final Map<Integer, JLabel> nodeCountLabels = new HashMap<>();

    public BoardPanel(YutnoriSet yutnoriSet) {
        this.boardGraph = new BoardGraph4();
        this.yutnoriSet = yutnoriSet;
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        // 모든 노드 버튼 생성 및 추가
        for (Map.Entry<Integer, Point> entry : boardGraph.getNodePositions().entrySet()) {
            int nodeId = entry.getKey();
            Point pos = entry.getValue();
            NodeButton btn = new NodeButton(nodeId, pos);
            btn.setEnabled(false); // 클릭 불가로 시작
            add(btn);
            nodeButtons.put(nodeId, btn);
        }
    }


    public void addInitialMalButton(int playerId, int malId, Point initialPos) {
        MalButton malBtn = new MalButton(playerId, malId, playerColors.get(playerId));
        malBtn.setLocation(initialPos.x - 10, initialPos.y - 10); // 중심 기준 위치 조정
        malBtn.addActionListener(e -> handleMalClick(playerId, malId, 0)); // nodeId = 0 기준으로 시작
        add(malBtn);
        setComponentZOrder(malBtn, 0);
        malButtons.add(malBtn);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoardGraph((Graphics2D) g);

        // 말 그리기
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Map.Entry<Integer, Point> entry : malPositions.entrySet()) {
            int key = entry.getKey();
            int playerId = key / 10;
            Point pos = entry.getValue();
            Color color = playerColors.getOrDefault(playerId, Color.BLACK);

            int r = 14;
            g2d.setColor(color);
            g2d.fillOval(pos.x - r / 2, pos.y - r / 2, r, r);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(pos.x - r / 2, pos.y - r / 2, r, r);
        }
    }

    private void drawBoardGraph(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.GRAY);

        for (int[] edge : boardGraph.getEdges()) {
            Point from = boardGraph.getNodePositions().get(edge[0]);
            Point to = boardGraph.getNodePositions().get(edge[1]);
            if (from != null && to != null) {
                g2.drawLine(from.x, from.y, to.x, to.y);
            }
        }

        for (Map.Entry<Integer, Point> entry : boardGraph.getNodePositions().entrySet()) {
            int id = entry.getKey();
            Point p = entry.getValue();
            g2.setColor(Color.WHITE);
            g2.fillOval(p.x - 15, p.y - 15, 30, 30);
            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - 15, p.y - 15, 30, 30);
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.drawString(String.valueOf(id), p.x - 6, p.y + 4);
        }
    }

    public void updateMalPosition(int[] data) {
        int playerId = data[0];
        int malId = data[1];
        int nodeId = data[2];

        Point pos = boardGraph.getNodePositions().get(nodeId);
        if (pos == null) return;

        // 기존 버튼 제거
        removeMalButton(playerId, malId);

        // 대표 말 버튼 생성
        MalButton malBtn = new MalButton(playerId, malId, playerColors.get(playerId));
        malBtn.setLocation(pos.x - 10, pos.y - 10);
        malBtn.setNodeId(nodeId);
        malBtn.addActionListener(e -> handleMalClick(playerId, malId, nodeId));
        add(malBtn);
        setComponentZOrder(malBtn, 0);
        malButtons.add(malBtn);

        //✅겹친 말 갯수 계산
        // 정확한 말 개수 계산: 실제 보드 정보 기반
        List<Mal> occupyingMals = yutnoriSet.getBoard().boardShape.get(nodeId).getOccupyingPieces();

        int totalCount = 0;
        for (Mal mal : occupyingMals) {
            totalCount += 1; // 대표 말
            totalCount += mal.getStackedMal().size(); // 그룹된 말 수 포함
        }

        // 라벨 표시
        if (totalCount >= 1) {
            JLabel label = nodeCountLabels.computeIfAbsent(nodeId, id -> {
                JLabel l = new JLabel();
                l.setFont(new Font("맑은 고딕", Font.BOLD, 12));
                l.setForeground(Color.BLACK);
                add(l);
                return l;
            });
            label.setText(totalCount + " ");
            label.setBounds(pos.x - 20, pos.y - 20, 30, 20);
            label.setVisible(true);
        } else {
            JLabel label = nodeCountLabels.get(nodeId);
            if (label != null) label.setVisible(false);
        }

        repaint();
    }



    //✅그룹화시 이동 전 노드의 말버튼 삭제
    private void removeMalButton(int playerId, int malId) {
        MalButton removed = null;
        for (MalButton btn : malButtons) {
            if (btn.getPlayerId() == playerId && btn.getMalId() == malId) {
                removed = btn;
                remove(btn);
                break;
            }
        }
        if (removed != null) {
            malButtons.remove(removed);
            int nodeId = removed.getNodeId();

            // 라벨 갱신
            long remaining = malButtons.stream()
                    .filter(m -> m.getNodeId() == nodeId && m.getPlayerId() == playerId)
                    .count();

            JLabel label = nodeCountLabels.get(nodeId);
            if (label != null) {
                if (remaining >= 1) {
                    label.setText(remaining + ".");
                    label.setVisible(true);
                } else {
                    label.setVisible(false);
                }
            }
        }
    }



    private void handleMalClick(int playerId, int malId, int currentNode) {
        this.selectedPlayerId = playerId;
        this.selectedMalId = malId;

        // 모든 노드를 비활성화 & 하이라이트 해제 & 이벤트 제거
        for (NodeButton b : nodeButtons.values()) {
            b.setHighlighted(false);
            b.setEnabled(false);
            for (ActionListener l : b.getActionListeners()) {
                b.removeActionListener(l);
            }
        }

        // 현재 플레이어의 이동 가능한 윷 결과 조회
        List<YutResult> results = yutnoriSet.getPlayerResults();
        if (results.isEmpty())
            return;

        YutResult result = results.getFirst(); // 첫 번째 윷 결과 사용
        List<Integer> moveable = yutnoriSet.showMoveableNodeId(currentNode, result);

        // 🟡 이동 가능한 노드를 강조 & 클릭 가능하게 설정
        for (int nodeId : moveable) {
            NodeButton btn = nodeButtons.get(nodeId);
            if (btn != null) {
                btn.setHighlighted(true);
                btn.setEnabled(true); // 클릭 가능하도록 활성화
                btn.addActionListener(e -> handleNodeClick(nodeId, result));
            }
        }

        repaint();
    }

    private void handleNodeClick(int nodeId, YutResult result) {
        int currentNode = -1;
        for (MalButton btn : malButtons) {
            if (btn.getPlayerId() == selectedPlayerId && btn.getMalId() == selectedMalId) {
                currentNode = btn.getNodeId();
                break;
            }
        }

        // 말 잡기 시도
        // ✅말 그룹화시 말이 시작되는 부분 노드에서는 그룹화 안되게 설정
        yutnoriSet.tryCatchMal(selectedPlayerId, nodeId);

        if (currentNode == 0) {
            // 노드 0번이면 선택된 말 하나만 이동
            yutnoriSet.moveMal(selectedPlayerId, selectedMalId, nodeId, result);
            updateMalPosition(new int[]{selectedPlayerId, selectedMalId, nodeId});
        } else {
            // 그 외의 경우: 그룹 말 전부 이동
            List<MalButton> group = new ArrayList<>();
            for (MalButton btn : malButtons) {
                if (btn.getPlayerId() == selectedPlayerId && btn.getNodeId() == currentNode) {
                    group.add(btn);
                }
            }

            for (MalButton btn : group) {
                int malId = btn.getMalId();
                yutnoriSet.moveMal(selectedPlayerId, malId, nodeId, result);
                updateMalPosition(new int[]{selectedPlayerId, malId, nodeId});
            }
        }

        yutnoriSet.deletePlayerResult(result);

        for (NodeButton btn : nodeButtons.values()) {
            btn.setHighlighted(false);
            btn.setEnabled(false);
            for (ActionListener l : btn.getActionListeners()) {
                btn.removeActionListener(l);
            }
        }

        repaint();
    }



    public void removeMalAt(int[] data) {
        int playerId = data[0];
        int nodeId = data[1];

        malButtons.removeIf(btn -> {
            if (btn.getPlayerId() == playerId && btn.getNodeId() == nodeId) {
                remove(btn);
                return true;
            }
            return false;
        });

        JLabel label = nodeCountLabels.get(nodeId);
        if (label != null) label.setVisible(false);

        repaint();
    }


    public BoardGraph getBoardGraph() {
        return this.boardGraph;
    }

    // 말 버튼 클릭 시 이벤트 처리
    // 나중에 외부로 빼야함



}
